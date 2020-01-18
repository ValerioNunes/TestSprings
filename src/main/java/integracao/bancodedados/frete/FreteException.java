package integracao.bancodedados.frete;

public class FreteException extends Exception {

    public FreteException(Exception e) throws FreteException {
        super(e);
    }

}