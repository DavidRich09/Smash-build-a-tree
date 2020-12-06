using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TokenSpawner : MonoBehaviour {

    public GameObject BST, AVL, B, SPLAY, Shield,Ataque, Salto, Vida;

    public float spawnRate = 2f;

    float nextSpawn = 0f;

    int whatToSpawn;

    public float RmoveSpeed = 1f;
    public float Lmovespeed = -1f;
    float movespeed;

    public Rigidbody2D rb;



    // Start is called before the first frame update
    void Start()
    {
        movespeed = RmoveSpeed;
    }

    // Update is called once per frame
    void Update()
    {
        if (rb.position.x >= 2f)
        {
            movespeed = Lmovespeed;
        }
        else if (rb.position.x <= -3f)
        {
            movespeed = RmoveSpeed;
        }

        rb.velocity = new Vector2(movespeed, 0);

        spawntoken("token",1);
    }

    public void spawntoken(string tipo,int valor)
    {
        if (Time.time > nextSpawn)
        {
            whatToSpawn = Random.Range(1, 9);
            //Debug.Log(whatToSpawn);


            
            if (whatToSpawn == 1)
            {
               
                Instantiate(BST, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("BST", whatToSpawn);
            }
            else if (whatToSpawn == 2)
            {
                Instantiate(AVL, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("AVL", whatToSpawn);
               
            }
            else if (whatToSpawn == 3)
            {
                Instantiate(SPLAY, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("SPLAY", whatToSpawn);
                
            }
            else if(whatToSpawn == 4)
            {
                Instantiate(B, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("B", whatToSpawn);
               
            }
            else if (whatToSpawn == 5)
            {
                Instantiate(Shield, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Shield");
            }
            else if (whatToSpawn == 6)
            {
                Instantiate(Ataque, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Ataque");
            }
            else if (whatToSpawn == 7)
            {
                Instantiate(Salto, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Salto");
            }
            else if (whatToSpawn == 8)
            {
                Instantiate(Vida, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Vida");
            }



            nextSpawn = Time.time + spawnRate;
        }
    }
}
