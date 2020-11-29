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

    //Movemen
    private Vector2 movimiento;
    private bool verDerecha = true;
    private bool inSuelo;

    //Ataque
    private bool ataque;

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
        
    }

    void Update()
    {
        //Movimiento
        movimiento = new Vector2(0, 0f);

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
        if (Input.GetKeyDown(jump) && inSuelo == true)
        {
            rigibody.AddForce(Vector2.up * salto, ForceMode2D.Impulse);
        }

        //Ataque
        if (Input.GetKeyDown(attack) && inSuelo == true && ataque == false)
        {
            movimiento = Vector2.zero;
            rigibody.velocity = Vector2.zero;
            animator.SetTrigger("attack");
        }

    }

    private void FixedUpdate()
    {
        float velhorizontal = movimiento.normalized.x * speed;
        rigibody.velocity = new Vector2(velhorizontal, rigibody.velocity.y);
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
}
