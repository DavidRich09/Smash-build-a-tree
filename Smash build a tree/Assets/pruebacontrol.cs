using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class pruebacontrol : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        float bruh = Input.GetAxis("Horizontal");

        Debug.Log(bruh);
    }
}
