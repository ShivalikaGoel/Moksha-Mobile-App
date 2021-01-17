using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using UnityEngine;
using System;
public class destructBubble : MonoBehaviour
{
    // Start is called before the first frame update
    public Renderer rend;
   
    void Start()
    {
       
        rend = GetComponent<Renderer>();
        rend.enabled = true;
    }

    // Update is called once per frame
    void Update()
    {
        // gameObject.AddForce(-Physics.gravity);
        
    }

 

    public AudioSource myFx;
    public AudioClip clickFx;
    

    void OnMouseDown()
    {
        myFx.PlayOneShot(clickFx);
         rend.enabled = false;

       // StartCoroutine(ShowAndHide(object, 5.0f));



    }

   
}

