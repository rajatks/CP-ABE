/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.parameters.PropertiesParameters;
import java.io.ByteArrayInputStream;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import java.util.Scanner;



class makesetup {
     String pairingDesc;
    Pairing p;
    Element g;
    Element h;
    Element f;
    Element gp;
    Element g_hat_alpha;
    Element beta; 
    Element g_alpha; 


	 String curveParams = "type a\n"
			+ "q 87807107996633125224377819847540498158068831994142082"
			+ "1102865339926647563088022295707862517942266222142315585"
			+ "8769582317459277713367317481324925129998224791\n"
			+ "h 12016012264891146079388821366740534204802954401251311"
			+ "822919615131047207289359704531102844802183906537786776\n"
			+ "r 730750818665451621361119245571504901405976559617\n"
			+ "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";

	    void setup() {
		Element alpha, beta_inv;

	PropertiesParameters params = new PropertiesParameters()
				.load(new ByteArrayInputStream(curveParams.getBytes()));

		pairingDesc = curveParams;
		p= PairingFactory.getPairing(params);
		Pairing pairing = p;

		g = pairing.getG1().newElement();
		f = pairing.getG1().newElement();
		h = pairing.getG1().newElement();
		gp = pairing.getG2().newElement();
		g_hat_alpha = pairing.getGT().newElement();
		alpha = pairing.getZr().newElement();
		beta = pairing.getZr().newElement();
	        g_alpha = pairing.getG2().newElement();

		alpha.setToRandom();
                
		beta.setToRandom();
		g.setToRandom();
               System.out.println("**************Setup Function:*****************");
              System.out.println("Element of group G1:");
               System.out.println("The value of g  is :");
                System.out.println(g);
	        gp.setToRandom();

		g_alpha = gp.duplicate();
		g_alpha.powZn(alpha);
                 System.out.println("g to the power alpha");
                 System.out.println(g_alpha);

		beta_inv = beta.duplicate();
		beta_inv.invert();
		f = g.duplicate();
	        f.powZn(beta_inv);
                 System.out.println("g to the power beta inverse");
                 System.out.println(f);

		h = g.duplicate();
		h.powZn(beta);
                 System.out.println("g to the power beta");
                System.out.println(h);

		g_hat_alpha = pairing.pairing(g,g_alpha);
                 System.out.println("billenear map of g and g alpha");
                 System.out.println("Element of group GT:");
                System.out.println(g_hat_alpha);
                
	}
            
 }
class user
{
  String attr;
Element a;
Element b;
}
class keygen
{
    Element d;
    ArrayList<user>comps=new ArrayList<user>();
    void secretkey(makesetup ms)
    {
        
        Pairing pair;
        pair=ms.p;
        d=pair.getG2().newElement();
        d=ms.g_alpha.duplicate();
        Element r;
        r=pair.getZr().newElement();
        r.setToRandom();
        Element gr;
         gr=pair.getG2().newElement();
         gr=ms.gp.duplicate();
         gr.powZn(r);
         d.mul(gr);
         Element bi;
         bi=pair.getZr().newElement();
         bi=ms.beta.duplicate();
         bi.invert();
         d.powZn(bi);
         System.out.println("**************************Key Generation Function***********************");
         
         System.out.println("The value of d  that is g to the power (alpha + r)/ beta is");
         System.out.println(d);
         
         Scanner sc=new Scanner(System.in);
         System.out.println("enter the number of attributes of user");
       
         int n=sc.nextInt();
         sc.nextLine();
         String[] attributes=new String[n];
         int i;
         System.out.println("enter the attributes");
         for(i=0;i<n;i++)
         {
             attributes[i]=sc.nextLine();
             
    }
          System.out.println("the attributes are :");
         for(i=0;i<n;i++)
         {
             System.out.println(attributes[i]);
         
    
}
         System.out.println("FOR EVERY ATTRIBUTE ");
         
         for(i=0;i<n;i++)
         {
             user u=new user();
             u.attr=attributes[i];
             Element rp;
             rp=pair.getZr().newElement();
             rp.setToRandom();
             u.a=pair.getG2().newElement();
             u.b=pair.getG1().newElement();
             u.a=gr.duplicate();
             Element h;
             h=pair.getG2().newElement();
             h.powZn(rp);
             u.a.mul(h);
             u.b=ms.g.duplicate();
             u.b.powZn(rp);
             comps.add(u);
             System.out.println(u.a);
             System.out.println(u.b);
         
         
             
             
             
         }
}
}
class polynomial
{
    int degree;
    Element[]coef;
}
class policy
{
    String attr;
    int k;
    policy[]child;
    Element c1 ;
    Element c2;
    polynomial q;
    boolean satisfy=false;
}
class cyphertext
{
    Element c;
    Element cs;
    policy p;
     Element[]ctext;
     Integer []ct;
    Element generate_ct(makesetup o)
    {
        
        Element s;
        Pairing pa=o.p;
        s=pa.getZr().newElement();
        c=pa.getG1().newElement();
       cs=pa.getGT().newElement();
        s.setToRandom();
        c=o.h.duplicate();
        c.powZn(s);
           System.out.println("**************************ENCRYPTION******************************");
        System.out.println("value of s is");
        System.out.println(s);
        
        System.out.println("value of c to the power s is");
        System.out.println(c);
        String message="abcdef";
        Scanner sc=new Scanner(System.in);
        int l=message.length();
        
        
        
      ct=new Integer[l];
      int i;
      for(i=0;i<l;i++)
      {
         ct[i]=(int)message.charAt(i);
      }
    
     
   
    
        cs=o.g_hat_alpha.duplicate();
        cs.powZn(s);
         System.out.println("value of cs to the power s is");
        System.out.println(cs);
        ctext=new Element[l];
       for(i=0;i<l;i++)
      {
         ctext[i]=cs.mul(ct[i]);
      }
       System.out.println("elements of element array");
       for(i=0;i<l;i++)
       {
           System.out.println(ctext[i]);
       }
        
        return s;
      
}
    policy access_structure(String cypher_policy)
    {
        String[] toks;
        String tok;
        ArrayList<policy>stack=new  ArrayList<>();
        toks=cypher_policy.split(" ");
        int index;
        index = toks.length;
        
        int i;
        
        for(i=0;i<index;i++)
        {
            int k,n;
            tok=toks[i];
            if(!tok.contains("of"))
            {
                policy node=new policy();
                node.attr=tok;
                node.k=1;
                stack.add(node);
                
                
               }
            else
            {
                String[]again=tok.split("of");
                k=Integer.parseInt(again[0]);
                n=Integer.parseInt(again[1]);
                policy node2=new policy();
                node2.attr=null;
                node2.k=k;
                node2.child=new policy[n];
                int it;
                for(it=n-1;it>=0;it--)
                {
                    node2.child[it]=new policy();
                    node2.child[it]=stack.remove(stack.size()-1);
                    
                    
                }
              
                stack.add(node2);
                
            }
        }
        p=stack.get(0);
       return p;
        
        
    }
    void fillPolicy(policy p,makesetup m,Element e)
    {
        int i;
		Element r, t, h;
		Pairing pairing = m.p;
		r = pairing.getZr().newElement();
		t = pairing.getZr().newElement();
		h = pairing.getG2().newElement();
                h=m.h.duplicate();

		p.q = randPoly(p.k - 1, e);

		if (p.child == null || p.child.length == 0) {
			p.c1 = pairing.getG1().newElement();
			p.c2 = pairing.getG2().newElement();

			
			p.c1 = m.g.duplicate();;
			p.c1.powZn(p.q.coef[0]); 	
			p.c2 = h.duplicate();
			p.c2.powZn(p.q.coef[0]);
		} else {
			for (i = 0; i < p.child.length; i++) {
				r.set(i + 1);
				evalPoly(t, p.q, r);
				fillPolicy(p.child[i], m, t);
			}
		}

	}
    void evalPoly(Element r, polynomial q, Element x) {
		int i;
		Element s, t;

		s = r.duplicate();
		t = r.duplicate();

		r.setToZero();
		t.setToOne();

		for (i = 0; i < q.degree + 1; i++) {
			
			s = q.coef[i].duplicate();
			s.mul(t); 
			r.add(s);

			
			t.mul(x);
		}

	}
     polynomial randPoly(int deg, Element zeroVal) {
		int i;
		polynomial q = new polynomial();
		q.degree = deg;
		q.coef = new Element[deg + 1];

		for (i = 0; i < deg + 1; i++)
			q.coef[i] = zeroVal.duplicate();

		q.coef[0].set(zeroVal);

		for (i = 1; i < deg + 1; i++)
			q.coef[i].setToRandom();

		return q;
	}
    }
class dec
{
    void decrypt(keygen sk,policy p)
    {
      
        
       

        if(p.attr!=null)
        {  
            
            int i=0;
            String at;
            at=p.attr;
          
            
            for(i=0;i<sk.comps.size();i++)
            {
                if(at.compareTo(sk.comps.get(i).attr)==0)
                {
                    p.satisfy=true;
                return;
                }
               
            }
            
        
        }
        else
        {
 
            int i;
             
              
             
            for(i=0;i<p.child.length;i++)
            {  
                
               
             
              decrypt(sk,p.child[i]);
            }
          
        }
        }
     
            
        
    
    boolean check(policy p)
    {
       
        int count=0;
        int i;
         
         
         for(i=0;i<p.child.length;i++)
         {
            if(p.child[i].satisfy==true)
            {
                count++;
            }
         }
         System.out.println(count);
         if(count>=p.k)
         {
             
             System.out.println("ACCESS GRANTED");
             
             return true;
         }
         else
         {
             
             System.out.println("ACCESS DENIED"); 
             return false;
         }
    }
         void getvalue(cyphertext t,keygen k,makesetup pub)
         {
         System.out.println("Working on fetching the decrypted value");
         System.out.println("The value of g to the power (alpha + r)/ beta is");
         System.out.println(k.d);
         System.out.println("The billenear map of h to the power s and g to the power (alpha + r)/ beta");
          
           Pairing pair;
           pair=pub.p;
          Element r1=pair.getG1().newElement();
          r1=k.d.duplicate();
            Element r2=pair.getG1().newElement();
            r2=t.c.duplicate();
              Element r3=pair.getGT().newElement();
              r3=pair.pairing(r1, r2);
              System.out.println(r3);
              System.out.println("divide the above obtained value with cs of cyphertext");
              r3.div(t.cs);
              System.out.println(r3);
              System.out.println("Hospital Recoards");
          

}
}
    



/**
 *
 * @author rajat
 */
public class Setup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
       makesetup ob=new makesetup();
       long time1;
       time1=System.currentTimeMillis();
       ob.setup();
       long time2=System.currentTimeMillis();
        System.out.println("time for setup in milli seconds");
       System.out.println(time2-time1);
       policy root;
       
       keygen key=new keygen();
       long keygentime1;
        keygentime1 =System.currentTimeMillis();
       key.secretkey(ob);
        long keygentime2;
        keygentime2 =System.currentTimeMillis();
        System.out.println("time for key generation in milli seconds");
       System.out.println(keygentime2-keygentime1);
       cyphertext text=new cyphertext();
       long enc1=System.currentTimeMillis();
       Element s_p=text.generate_ct(ob);
       long enc12=System.currentTimeMillis();
       
      
       System.out.println(s_p);
       Scanner sc=new Scanner(System.in);
       System.out.println("enter the cypher policy");
       String s=sc.nextLine();
        long enc2=System.currentTimeMillis();
       text.p=text.access_structure(s);
       System.out.println(text.p.k);
       long enc22=System.currentTimeMillis();
       long enc3=System.currentTimeMillis();
       text.fillPolicy(text.p, ob, s_p);
       long enc32=System.currentTimeMillis();
       int i;
       System.out.println("time for encryption");
       System.out.println((enc12-enc1)+(enc22-enc2)+(enc32-enc3));
   
     dec d1=new dec();
     long dect=System.currentTimeMillis();
     d1.decrypt(key,text.p);
    

      
     boolean x;
     
     x=d1.check(text.p);
     if(x)
     {
          
         d1.getvalue(text,key,ob);
     }
      long fdect=System.currentTimeMillis();
       System.out.println("time for decryption");
      System.out.println(fdect-dect);


       
       
      
       
       
       
       
       
      
       
       
       
    }
    
}

