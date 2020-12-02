using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player2 : MonoBehaviour
{
    //Velocidad
    public float speed = 2.5f;
    public float salto = 5f;
    

    //Colisión suelo
    public Transform verificaSuelo;
    public LayerMask layerSuelo;
    public float radioCheck;

    //Referencias
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
    public float attackrange;
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
    public float turboSalto = 2f;


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
    }

    void Update()
    {

        //parar el empuje
        if (empuje)
        {
            timer += Time.deltaTime;

            if (timer > duracion)
            {
                timer = timer - duracion;
                empuje = false;
            }
        }

        //movimiento
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

        }
        else if (Input.GetKey(moveL))
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

        //Activar escudo
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
        }
        else
        {
            ataque = false;
        }


    }

    private void Voltear()
    {
        verDerecha = !verDerecha;
        float localScaleX = transform.localScale.x;
        localScaleX = localScaleX * -1f;
        transform.localScale = new Vector3(localScaleX, transform.localScale.y, transform.localScale.z);
    }


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

    public void Push(float fuerza)
    {
        empuje = true;
        rigibody.AddForce(new Vector2(fuerza, rigibody.velocity.y), ForceMode2D.Impulse);
    }

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

    private void OnTriggerEnter2D(Collider2D collision)
    {
        GameObject token = collision.gameObject;
        string tag = token.tag;

        if (tag.Equals("Token"))
        {
            //logica de ver cua token es
            //Mander informacion al cliente
        }
        else if (tag.Equals("Poder"))
        {
            int poder = 1;
            //int poder = token.GetComponent<Poder>().getPoder();

            switch (poder)
            {
                case 1:
                    poderAtaque = true;
                    break;

                case 2:
                    escudo = true;
                    EscudoOBJ.SetActive(true);
                    break;

                case 3:
                    break;

                case 4:
                    break;
            }
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
