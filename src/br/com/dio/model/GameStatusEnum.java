package br.com.dio.model;

public enum GameStatusEnum {
    NON_STARTED("Não Iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    //label para exibir os status
    private String label;

    GameStatusEnum(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
