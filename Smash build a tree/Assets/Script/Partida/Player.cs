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
        float entradahorizontal = Input.GetAxisRaw("Horizontal");
        movimiento = new Vector2(entradahorizontal, 0f);

        //Voltear
        if (entradahorizontal < 0f && verDerecha == true)
        {
            Voltear();
        } else if (entradahorizontal > 0f && verDerecha == false)
        {
            Voltear();
        }

        //Esta en el suelo
        inSuelo = Physics2D.OverlapCircle(verificaSuelo.position, radioCheck, layerSuelo);

        if (Input.GetButtonDown("Jump") && inSuelo == true)
        {
            rigibody.AddForce(Vector2.up * salto, ForceMode2D.Impulse);
        }

        //Ataque
        if (Input.GetButtonDown("Fire1") && inSuelo == true && ataque == false)
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
            Debug.Log("dad");

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
