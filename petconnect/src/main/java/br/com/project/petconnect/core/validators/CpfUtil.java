package br.com.project.petconnect.core.validators;

import br.com.project.petconnect.app.user.domain.entities.UserEntity;

public class CpfUtil {

    // adiciona máscara ao cpf
    public static String addMask(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // remove máscara ao cnpj
    public static String removeMask(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    public static void setCpf(UserEntity user, String cpf) {
        if (cpf != null && !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            cpf = addMask(cpf);
        }
        user.setCpf(cpf);
    }
}
