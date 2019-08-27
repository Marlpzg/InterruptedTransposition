package interruptedtransposition;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.IntStream;

public class InterruptedTransposition {

    private static void showMenu(){
        System.out.println("----------------Menú Principal----------------");
        System.out.println("¿Qué desea hacer?");
        System.out.println("\t1. Encriptar.");
        System.out.println("\t2. Desencriptar.");
        System.out.println("\t3. Salir.");
        System.out.print("Seleccione una opción: ");
    }
    
    private static int[] getKey(String key){
        
        char[] keyArray = key.toCharArray();
        int[] keyNumbers = IntStream.rangeClosed(1, keyArray.length).toArray();
        
        int[] convertedKey = new int[keyArray.length];
        Arrays.sort(keyArray);
        
        for (int i = 0; i < keyArray.length; i++) {
            for (int j = 0; j < key.length(); j++) {
                if(keyArray[i] == key.charAt(j) && convertedKey[j] == 0){
                    convertedKey[j] = keyNumbers[i];
                    break;
                }
            }
        }
        
        return convertedKey;
    }
    
    private static String encriptar(String texto, String key){
        String cadena = "";//Resultado
        
        String textoMod = texto.replaceAll("\\s+","").toUpperCase();//Texto sin espacios
        int iterador = 0;//Posicion de la clave
        int caracteres = 0;//Contador de caracteres
        
        int modifier = 0;//Posicion del encriptado
        
        int[] keyArray = getKey(key.replaceAll("\\s+",""));//Clave de encriptado
        ArrayList<char[]> matriz = new ArrayList<>();//Matriz de encriptado

        while(caracteres < textoMod.length()){
            
            matriz.add(new char[keyArray.length]);
            int tofind = (iterador%keyArray.length)+1;
            for (int i = 0; i < keyArray.length; i++) {
                if(tofind == keyArray[i]){
                    modifier = i+1;
                    break;
                }
            }
            
            int limit = caracteres+modifier;
            if(limit>=textoMod.length()){
                limit -= limit%textoMod.length();
            }
                       
            int j = 0;
            for (int i = caracteres; i < limit; i++) {
                matriz.get(iterador)[j] = textoMod.charAt(i);
                j++;
            }
            iterador++;
            
            caracteres += modifier;
            
        }
        
        for (int j = 0; j < keyArray.length; j++) {
            int k=0;
            for (int i = 0; i < keyArray.length; i++) {
                if(j+1 == keyArray[i]){
                    k = i;
                    break;
                }
            }
            boolean flag = false;
            for (int i = 0; i < matriz.size(); i++) {
                char c = matriz.get(i)[k];
                if(c != '\u0000'){
                    cadena += c;
                    flag = true;
                }
            }
            if(flag)
                cadena += " ";
        }
        
        System.out.println(cadena+"\n");
        return cadena;
    }
    
    private static String desencriptar(String texto, String key){
        String cadena = "";//Resultado
        
        String textoMod = texto.replaceAll("\\s+","").toUpperCase();//Texto sin espacios
        int iterador = 0;//Posicion de la clave
        int caracteres = 0;//Contador de caracteres
        
        int modifier = 0;//Posicion del encriptado
        
        int[] keyArray = getKey(key.replaceAll("\\s+",""));//Clave de encriptado
        ArrayList<char[]> matriz = new ArrayList<>();//Matriz de encriptado

        while(caracteres < textoMod.length()){
            
            matriz.add(new char[keyArray.length]);
            int tofind = (iterador%keyArray.length)+1;
            for (int i = 0; i < keyArray.length; i++) {
                if(tofind == keyArray[i]){
                    modifier = i+1;
                    break;
                }
            }
            
            int limit = caracteres+modifier;
            if(limit>=textoMod.length()){
                limit -= limit%textoMod.length();
            }
                       
            int j = 0;
            for (int i = caracteres; i < limit; i++) {
                matriz.get(iterador)[j] = '-';
                j++;
            }
            iterador++;
            
            caracteres += modifier;
            
        }

        int charpos = 0;
        for (int j = 0; j < keyArray.length; j++) {
            int k=0;
            for (int i = 0; i < keyArray.length; i++) {
                if(j+1 == keyArray[i]){
                    k = i;
                    break;
                }
            }

            for (int i = 0; i < matriz.size(); i++) {
                char c = matriz.get(i)[k];
                if(c == '-'){
                    matriz.get(i)[k]=textoMod.charAt(charpos);
                    charpos++;
                }
            }

        }
        
        for (int i = 0; i < matriz.size(); i++) {
                for (int l = 0; l < keyArray.length; l++) {
                    cadena += matriz.get(i)[l];
                }
            }
        
        System.out.println(cadena+"\n");
        return cadena;
    }
    
    public static void main(String[] args) {
        int opc;
        Scanner leer = new Scanner(System.in);
        String texto;
        String clave;
        
        while(true){
            showMenu();
            try{
                opc = leer.nextInt();
                leer.nextLine();
                if(opc == 1){
                    System.out.print("Ingrese el texto a encriptar: ");
                    texto = leer.nextLine();
                    System.out.print("Ingrese la palabra clave: ");
                    clave = leer.nextLine();
                    encriptar(texto,clave);
                }else if(opc == 2){
                    System.out.print("Ingrese el texto a desencriptar: ");
                    texto = leer.nextLine();
                    System.out.print("Ingrese la palabra clave: ");
                    clave = leer.nextLine();
                    desencriptar(texto,clave);
                }else if(opc == 3){
                    break;
                }else{
                    System.out.println("Seleccione una opción del menú.\n");
                }
            }catch(Exception e){
                System.out.println("Debe ingresar una opción válida.\n");
            }
        }

    }   
}
