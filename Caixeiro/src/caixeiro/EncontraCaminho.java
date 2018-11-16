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
public class EncontraCaminho implements Runnable {    //classe que encontra menor caminho e implementa o padrão Runnable
    //parâmetros a serem utilizados pela classe

    private String buffer;
    private String linhas[];
    private Integer mat[][];
    private Integer vetUsados[];
    private Integer custoTotal = 0;
    private Integer quantNo = -1;
    private Integer noAtual = -1;
    private Integer noInicial = -1;
    private boolean error = false;
    private boolean pronto = false;

    @Override
    public void run() {
        if ((!buffer.isEmpty()) && (buffer != null) && (buffer != "")) {
            linhas = buffer.split("/");
            try {
                noInicial = Integer.parseInt(linhas[0]);
                noAtual = noInicial;
                quantNo = Integer.parseInt(linhas[1]);
                linhas = linhas[2].split(":");
                mat = new Integer[linhas.length][3];

                for (int i = 0; i < linhas.length; i++) {
                    String distancia[] = linhas[i].split(" ");
                    mat[i][0] = Integer.parseInt(distancia[0]);
                    mat[i][1] = Integer.parseInt(distancia[1]);
                    mat[i][2] = Integer.parseInt(distancia[2]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Algum valor foi passado incorretamente");
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        if ((quantNo > 2) && (noAtual >= 0)) {
            vetUsados = new Integer[quantNo];
            Integer quantUsado = 0;
            Integer peso = -1;
            Integer proxNo = -1;
            boolean usado;

            vetUsados[0] = noAtual;
            for (int i = 1; i < vetUsados.length; i++) {
                vetUsados[i] = -1;
            }
            while ((quantUsado < quantNo) && !error) {
                for (int i = 0; i < linhas.length; i++) {
                    if (mat[i][0] == noAtual) {
                        usado = false;
                        for (int j = 0; j < quantNo; j++) {
                            if (mat[i][2] == vetUsados[j]) {
                                usado = true;
                            }
                        }
                        if ((vetUsados[quantNo - 1] != -1) && (mat[i][2]) == noInicial) {
                            usado = false;
                        }
                        if (!usado) {
                            if (peso == -1) {
                                peso = mat[i][1];
                                proxNo = mat[i][2];
                            } else if (peso > mat[i][1]) {
                                peso = mat[i][1];
                                proxNo = mat[i][2];
                            }
                        }
                    }
                }
                if (peso != -1) {
                    custoTotal += peso;
                    quantUsado++;
                    noAtual = proxNo;
                    if (quantUsado < quantNo) {
                        vetUsados[quantUsado] = noAtual;
                    }
                    proxNo = -1;
                    peso = -1;
                } else {
                    System.out.println("Não foi possível encontrar um caminho");
                    error = true;
                }
            }
        }

        buffer = custoTotal.toString() + ": ";
        for (Integer num : vetUsados) {
            buffer += num + " ";
        }
        buffer += noInicial;
        this.pronto = true;
    }

    public EncontraCaminho(String buffer) {
        this.buffer = buffer;
    }

    public EncontraCaminho() {
        this.pronto = false;
    }

    public void setBuffer(String exBuffer) {    //métodos get() e set()
        this.buffer = exBuffer;
    }

    public boolean getPronto() {
        return this.pronto;
    }

    public void setPronto(boolean value) {
        this.pronto = value;
    }

    public String getBuffer() {
        if (pronto) {
            return this.buffer;
        } else {
            return null;
        }
    }

    public Integer getCustoTotal() {
        return this.custoTotal;
    }
}
