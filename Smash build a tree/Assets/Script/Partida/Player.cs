using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class Player : MonoBehaviour
{
    //Velocidad
    public float speed = 2.5f;
    public float salto = 2.5f;
    public float tiempoespera = 5f;

    //Colisión suelo
    public Transform verificaSuelo;
    public LayerMask layerSuelo;
    public float radioCheck;

    //Referencias
    private float tiempoidle;
    private Rigidbody2D rigibody;
    private Animator animator;

    //Movement
    private Vector2 movimiento;
    private bool verDerecha = true;
    private bool inSuelo;

    //Ataque
    private bool ataque;
    private bool empuje = false;
    private float timer = 0.0f;
    private float duracion = 0.3f;
    public Transform attackpoint;
    public float attackrange = 0.6f;
    public LayerMask EnemyLayer;

    public float fuerzaempuje = 1f;
    

    //poderes
    public bool poderAtaque = false;
    public float fuerzataque = 6f;

    public GameObject EscudoOBJ;
    public bool escudo = false;
    private float escudotimer = 0.0f;
    public float escudotiempo = 30f;

    public bool poderSalto = false;
    public float turboSalto = 3f;
    LayerMask mylayer;

    //Keys

    public KeyCode moveR;
    public KeyCode moveL;
    public KeyCode jump;
    public KeyCode attack;

    private void Awake()
    {
        rigibody = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
    }

    void Start()
    {
        if (escudo)
        {
            EscudoOBJ.SetActive(true);
        }

        mylayer = gameObject.layer;

    }

    void Update()
    {

        
        if(empuje)
        {
            timer += Time.deltaTime;

            if (timer > duracion)
            {
                timer = timer - duracion;
                empuje = false;
            }
        }

        
        //Movimiento
        if (!empuje)
        {
            movimiento = new Vector2(0, 0f);
        }
        

        if (Input.GetKey(moveR))
        {
            movimiento = new Vector2(1, 0f);

            if (verDerecha == false)
            {
                Voltear();
            }

        } else if (Input.GetKey(moveL))
        {
            movimiento = new Vector2(-1, 0f);

            if (verDerecha == true)
            {
                Voltear();
            }
        }


        //Esta en el suelo
        inSuelo = Physics2D.OverlapCircle(verificaSuelo.position, radioCheck, layerSuelo);

        //Salto
        if (Input.GetKeyDown(jump))
        {
            if (poderSalto == true && inSuelo == false)
            {
                poderSalto = false;
                rigibody.AddForce(Vector2.up * salto * turboSalto, ForceMode2D.Impulse);
            }

            else if (inSuelo == true)
            {
                rigibody.AddForce(Vector2.up * salto, ForceMode2D.Impulse);
            }
        }

        //Ataque
        if (Input.GetKeyDown(attack) && inSuelo == true && ataque == false && poderAtaque == true)
        {
            movimiento = Vector2.zero;
            rigibody.velocity = Vector2.zero;
            animator.SetTrigger("attack");
            Attack();
            poderAtaque = false;
        }

        //Activar Escudo
        if (escudo)
        {
            escudotimer += Time.deltaTime;

            if (escudotimer > escudotiempo)
            {
                escudotimer = escudotimer - escudotiempo;
                escudo = false;
                EscudoOBJ.SetActive(false);
            }
        }
    }

    private void FixedUpdate()
    {
        if (!empuje)
        {
            float velhorizontal = movimiento.normalized.x * speed;
            rigibody.velocity = new Vector2(velhorizontal, rigibody.velocity.y);
        }
        
    }

    private void LateUpdate()
    {
        animator.SetBool("idle", movimiento == Vector2.zero);
        animator.SetBool("EsSuelo", inSuelo);
        animator.SetFloat("Vvelocity", rigibody.velocity.y);

        if (animator.GetCurrentAnimatorStateInfo(0).IsTag("Attacking"))
        {
            ataque = true;
        } else
        {
            ataque = false;
        }

        //idle largo
        if (animator.GetCurrentAnimatorStateInfo(0).IsTag("idletime"))
        {
            tiempoidle += Time.deltaTime;

            if (tiempoidle >= tiempoespera)
            {
               
                animator.SetTrigger("LongIdle");
            }
        } else {
            tiempoidle = 0f;
        }
    }

    private void Voltear()
    {
        verDerecha = !verDerecha;
        float localScaleX = transform.localScale.x;
        localScaleX = localScaleX * -1f;
        transform.localScale = new Vector3(localScaleX, transform.localScale.y, transform.localScale.z);
    }

    //Detectar colisiones
    private void OnCollisionEnter2D(Collision2D collision)
    {

        GameObject player = collision.gameObject;


        if (player.tag.Equals("Player1"))
        {
            if (escudo == false)
            {
                bool derecha = player.GetComponent<Player>().getVerDerecha();

                if (derecha)
                {
                    Push(fuerzaempuje);
                }
                else
                {
                    Push(-fuerzaempuje);
                }
            }
            
        }
        else if (player.tag.Equals("Player2"))
        {
            if (escudo == false)
            {
                bool derecha = player.GetComponent<Player2>().getVerDerecha();

                if (derecha)
                {
                    Push(fuerzaempuje);
                }
                else
                {
                    Push(-fuerzaempuje);
                }
            }
        }
    }

    public bool getVerDerecha()
    {
        return verDerecha;
    }

    //Push crea una fuerza en el rigid body 
    public void Push(float fuerza)
    {
        empuje = true;
        rigibody.AddForce(new Vector2(fuerza, rigibody.velocity.y), ForceMode2D.Impulse);
    }

    //Ataque busca si algun jugador esta dentro del hitbox y aplica una fuerza
    void Attack()
    {
        Collider2D[] playertoPush = Physics2D.OverlapCircleAll(attackpoint.position, attackrange, EnemyLayer);

        for (int i = 0; i < playertoPush.Length; i++)
        {

            float fuerza = fuerzataque;
            if (!verDerecha)
            {
                fuerza = -fuerzataque;
            }

            if (playertoPush[i].gameObject.tag.Equals("Player1"))
            {
                if (playertoPush[i].GetComponent<Player>().escudo == false)
                {
                    playertoPush[i].gameObject.GetComponent<Player>().Push(fuerza);
                }

            }
            else if (playertoPush[i].gameObject.tag.Equals("Player2"))
            {
                if (playertoPush[i].GetComponent<Player2>().escudo == false)
                {
                    playertoPush[i].gameObject.GetComponent<Player2>().Push(fuerza);

                }
            }
        }
    }

    //Detectar si se hagarro un token
    private void OnTriggerStay2D(Collider2D collision)
    {
        GameObject token = collision.gameObject;
        string tag = token.tag;

        if (tag.Equals("Token"))
        {
            string jugadorname = gameObject.transform.parent.name;

            collision.gameObject.GetComponent<Token>().SendToken(jugadorname);
            
            Destroy(token);
        }
        else if(tag.Equals("Poder"))
        {
            string poder = token.GetComponent<Poder>().GetTipo();
   
            switch (poder)
            {
                case "Ataque":
                    poderAtaque = true;
                    break;

                case "Shield":
                    escudo = true;
                    EscudoOBJ.SetActive(true);
                    break;

                case "Salto":
                    poderSalto = true;
                    break;

                case "Vida":
                    if (mylayer == LayerMask.NameToLayer("Player-1"))
                    {
                        ControllerSelection controller = ControllerSelection.GetInstancia();
                        controller.GetComponent<ControllerSelection>().SumarVida();
                    }
                    else if (mylayer == LayerMask.NameToLayer("Player-2"))
                    {
                        ControllerSelection2 controller = ControllerSelection2.GetInstancia();
                        controller.GetComponent<ControllerSelection2>().SumarVida();
                    }
                    else if (mylayer == LayerMask.NameToLayer("Player-3"))
                    {
                        ControllerSelection3 controller = ControllerSelection3.GetInstancia();
                        controller.GetComponent<ControllerSelection3>().SumarVida();
                    }
                    else if (mylayer == LayerMask.NameToLayer("Player-4"))
                    {
                        ControllerSelection4 controller = ControllerSelection4.GetInstancia();
                        controller.GetComponent<ControllerSelection4>().SumarVida();
                    }
                    break;
            }
            Destroy(token);
        }
    }

    public void OnDrawGizmosSelected()
    {
        if (attackpoint == null)
        {
            return;
        }

        Gizmos.DrawWireSphere(attackpoint.position, attackrange);
    }

    
    
      


}
