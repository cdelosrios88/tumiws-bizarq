package pe.com.tumi.cobranza.test.controller;

public class Max_Min {
	public static void main(String[] args){
		int n = 0,menor = 99999;
		for (int i = 0; i < 10; i++) {
			System.out.println("Ingresa un numero");
			n = i;
			if(n < menor){
			menor = n;
			}
			}
			
			System.out.println("El numero menor es :" + menor);
		}
 }
