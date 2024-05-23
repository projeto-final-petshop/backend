package br.com.project.petconnect.core.validators;

import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;

public class CnpjUtil {

    // Adicionar máscara ao CNPJ
    public static String addMask(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    // Remover máscara do CNPJ
    public static String removeMask(String cnpj) {
        return cnpj.replaceAll("\\D", "");
    }

    public void setCpf(PetShopEntity petShop, String cnpj) {
        if (cnpj != null && !cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}")) {
            cnpj = addMask(cnpj);
        }
        petShop.setCnpj(cnpj);
    }

}
