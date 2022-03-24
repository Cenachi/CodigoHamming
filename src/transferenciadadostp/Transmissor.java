package transferenciadadostp;

import java.util.Random;

public class Transmissor {
    private String mensagem;

    public Transmissor(String mensagem) {
        this.mensagem = mensagem;
    }

    // convertendo um símbolo para "vetor" de boolean (bits)
    private boolean[] streamCaracter(char simbolo) {

        // cada símbolo da tabela ASCII é representado com 8 bits
        boolean bits[] = new boolean[8];

        // convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
        int valorSimbolo = (int) simbolo;
        int indice = 7;

        // convertendo cada "bits" do valor da tabela ASCII
        while (valorSimbolo >= 2) {
            int resto = valorSimbolo % 2;
            valorSimbolo /= 2;
            bits[indice] = (resto == 1);
            indice--;
        }
        bits[indice] = (valorSimbolo == 1);

        return bits;
    }

    // não modifique (seu objetivo é corrigir esse erro gerado no receptor)
    private void geradorRuido(boolean bits[]) {
        Random geradorAleatorio = new Random();

        // pode gerar um erro ou não..
        if (geradorAleatorio.nextInt(5) > 1) {
            int indice = geradorAleatorio.nextInt(8);
            bits[indice] = !bits[indice];
        }
    }

    private boolean[] dadoBitsHemming(boolean bits[]) {

        boolean hemmingBits[] = new boolean[4];

        hemmingBits[0] = bits[0] ^ bits[1] ^ bits[3] ^ bits[4] ^ bits[6];
        hemmingBits[1] = bits[0] ^ bits[2] ^ bits[3] ^ bits[5] ^ bits[6];
        hemmingBits[2] = bits[1] ^ bits[2] ^ bits[3] ^ bits[7];
        hemmingBits[3] = bits[4] ^ bits[5] ^ bits[6] ^ bits[7];

        boolean codigoHemming[] = {

                hemmingBits[0], hemmingBits[1], bits[0], hemmingBits[2],
                bits[1], bits[2], bits[3], hemmingBits[3],
                bits[4], bits[5], bits[6], bits[7]
        };
        
        return codigoHemming;
    }

    public void enviaDado(Receptor receptor) {
        for (int i = 0; i < this.mensagem.length(); i++) {
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));

            /*-------AQUI você deve adicionar os bits de Hemming para contornar os problemas de ruidos
                        você pode modificar o método anterior também*/
            boolean bitsHemming[] = dadoBitsHemming(bits);

            // add ruidos na mensagem a ser enviada para o receptor
            geradorRuido(bitsHemming);
            
            // enviando a mensagem "pela rede" para o receptor
            receptor.receberDadoBits(bitsHemming);
        }
    }
}
