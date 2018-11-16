/*

Brayan Rawlisson Castorio 0004016
Isabella Regina Campos de Faria 0011483
Mayron Reis Lacerda Ribeiro 0011408                        
Paulinelly de Sousa Oliveira 0011526
Rener Gonçalves de Pádua Junior 0015753  

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixeiro;

/**
 *
 * @author Engenharia de Computação 01
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Caixeiro {
	private static String sBuffer;
	private static EncontraCaminho busca1;   // buscas que serão entregues as threads filhas
	private static EncontraCaminho busca2;
	private static EncontraCaminho busca3;
	private static Thread threadBusca1;      //definição das threads filhas 
	private static Thread threadBusca2;
	private static Thread threadBusca3;
	
	public static void main(String[] args) throws IOException {
		sBuffer="";
		Integer numeroNos=-1;
		BufferedReader br = new BufferedReader(new FileReader("grafo.txt"));
		try {
			String linha=br.readLine();
			numeroNos=Integer.parseInt(linha);
			if (linha!=null){
				sBuffer = linha+"/";
				linha = br.readLine();				
			}
			while(linha!=null){
				sBuffer += linha+":";
				linha = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Não foi possível abrir o arquivo");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Não foi possível identificar o número de nós");
			e.printStackTrace();
		}finally {
		    br.close();
		}
		if (numeroNos>=0){                   // o randon seŕa para sortear onde cara thread vai começar a busca (cada uma começa em um nó aleatório)
			Random r= new Random();
			Integer n1, n2, n3;
			n1 = r.nextInt(numeroNos);
			n2 = r.nextInt(numeroNos);
			while(n2==n1){
				n2 = r.nextInt(numeroNos);
			}
			n3 = r.nextInt(numeroNos);
			while((n3==n1)||(n3==n2)){
				n3 = r.nextInt(numeroNos);
			}
			
			busca1 = new EncontraCaminho();
			busca2 = new EncontraCaminho();
			busca3 = new EncontraCaminho();
			
			busca1.setBuffer(n1.toString()+"/"+sBuffer);  // cada uma das buscas começara de um nó aleatório
			busca2.setBuffer(n2.toString()+"/"+sBuffer);
			busca3.setBuffer(n3.toString()+"/"+sBuffer);
			
			busca1.setPronto(false);
			busca2.setPronto(false);
			busca3.setPronto(false);
			
			threadBusca1 = new Thread(busca1);    // threads recem a tarefa de executar a busca
			threadBusca2 = new Thread(busca2);
			threadBusca3 = new Thread(busca3);
			
			threadBusca1.start();                    // threads começam a executar a busca
			threadBusca2.start();
			threadBusca3.start();
			
			while((!busca1.getPronto())||(!busca2.getPronto()||(!busca3.getPronto()))){}
                        // quando as três threads terminarem a busca, será escolhida a com a menor rota (no caso de mais de um thread encontre a menor rota, todas essas serão retornadas)
			int c1, c2, c3;
			try{
				c1=Integer.parseInt(busca1.getBuffer().split(":")[0]);
				c2=Integer.parseInt(busca2.getBuffer().split(":")[0]);
				c3=Integer.parseInt(busca3.getBuffer().split(":")[0]);
				
				if((c1==c2)&&(c1==c3)){
					System.out.println(busca1.getBuffer());
					System.out.println(busca2.getBuffer());
					System.out.println(busca3.getBuffer());
				} else {
					if(c1==c2){
						if(c1<c3){
							System.out.println(busca1.getBuffer());
							System.out.println(busca2.getBuffer());
						} else {
							System.out.println(busca3.getBuffer());
						}
					} else if(c1==c3){
						if(c1<c2){
							System.out.println(busca1.getBuffer());
							System.out.println(busca3.getBuffer());
						} else {
							System.out.println(busca2.getBuffer());
						}
					} else if(c2==c3){
						if(c2<c1){
							System.out.println(busca2.getBuffer());
							System.out.println(busca3.getBuffer());
						} else {
							System.out.println(busca1.getBuffer());
						}
					} else if(c1<c2 && c1<c3){
						System.out.println(busca1.getBuffer());
					} else if(c2<c1 && c2<c3){
						System.out.println(busca2.getBuffer());
					} else if(c3<c1 && c3<c2){
						System.out.println(busca3.getBuffer());
					} 
				}
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}			
		}else{
			System.out.println("Quantidade de nós não identificada.");
		}
	}
}