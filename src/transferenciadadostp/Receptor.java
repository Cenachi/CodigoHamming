package transferenciadadostp;

public class Receptor {

    // mensagem recebida pelo transmissor
    private String mensagem;

    public Receptor() {
        // mensagem vazia no inicio da execução
        this.mensagem = "";
    }

    public String getMensagem() {
        return mensagem;
    }

    private void decodificarDado(boolean bits[]) {
        int codigoAscii = 0;
        int expoente = bits.length - 1;

        // converntendo os "bits" para valor inteiro para então encontrar o valor tabela
        // ASCII
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;
        }

        // concatenando cada simbolo na mensagem original
        this.mensagem += (char) codigoAscii;
    }

    private void decoficarDadoHemming(boolean bits[]) {

        // implemente a decodificação Hemming aqui e encontre os
        // erros e faça as devidas correções para ter a imagem correta

        boolean hemmingBits[] = new boolean[4];

        hemmingBits[0] = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        hemmingBits[1] = bits[1] ^ bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        hemmingBits[2] = bits[3] ^ bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
        hemmingBits[3] = bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];

        int indice = Integer.parseInt(
                (((hemmingBits[3]) ? "1" : "0") + ((hemmingBits[2]) ? "1" : "0") + ((hemmingBits[1]) ? "1" : "0")
                        + ((hemmingBits[0]) ? "1" : "0")),
                2);

        boolean originais[] = new boolean[8];

        for (int i = 0; i < bits.length; i++) {
            if (i != 0 && i == indice - 1) {
                bits[i] = !(bits[i]);
            } else {
                bits[i] = bits[i];
            }
        }

        originais[0] = bits[2];
        originais[1] = bits[4];
        originais[2] = bits[5];
        originais[3] = bits[6];
        originais[4] = bits[8];
        originais[5] = bits[9];
        originais[6] = bits[10];
        originais[7] = bits[11];

        decodificarDado(originais);
    }

    // recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]) {

        // aqui você deve trocar o médodo decofificarDado para decoficarDadoHemming
        // (implemente!!)

        decoficarDadoHemming(bits);
    }
}