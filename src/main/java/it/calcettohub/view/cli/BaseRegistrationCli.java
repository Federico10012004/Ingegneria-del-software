package it.calcettohub.view.cli;

import it.calcettohub.bean.RegistrationBean;

public abstract class BaseRegistrationCli<T extends RegistrationBean> extends CliContext {

    protected abstract void fillSpecificFields(T bean);

    protected void fillCommonFields(T bean) {
        validateBeanField(()-> bean.setName(requestString("Nome: ")));
        validateBeanField(()-> bean.setSurname(requestString("Cognome: ")));
        validateBeanField(()-> bean.setDateOfBirth(requestDate("Data di nascita (gg-mm-aaaa): ")));
        validateBeanField(()-> bean.setEmail(requestString("Email: ")));
        validateBeanField(()-> bean.setPassword(requestString("Password: ")));
        validateBeanField(()-> bean.setConfirmPassword(requestString("Conferma password: ")));
    }

    public void validateAllFields(T bean) {
        fillCommonFields(bean);
        fillSpecificFields(bean);
    }
}
