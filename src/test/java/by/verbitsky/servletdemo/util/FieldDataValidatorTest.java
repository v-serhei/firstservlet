package by.verbitsky.servletdemo.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FieldDataValidatorTest {

    @Test
    public void testIsValidUserNamePositive() {
        String name = "username";
        Assert.assertTrue(FieldDataValidator.isValidUserName(name));
    }

    @Test
    public void testIsValidUserNameNegativeNullParameter() {
        Assert.assertFalse(FieldDataValidator.isValidUserName(null));
    }

    @Test
    public void testIsValidUserNameNegativeWrongParameter() {
        String name = "username<>";
        Assert.assertFalse(FieldDataValidator.isValidUserName(name));
    }

    @Test
    public void testIsValidUserEmailPositive() {
        String email = "email@mail.ru";
        Assert.assertTrue(FieldDataValidator.isValidUserEmail(email));
    }

    @Test
    public void testIsValidUserEmailNegativeNullParameter() {
        Assert.assertFalse(FieldDataValidator.isValidUserEmail(null));
    }    @Test
    public void testIsValidUserEmailNegativeWrongEmail() {
        String email = "email";
        Assert.assertFalse(FieldDataValidator.isValidUserEmail(email));
    }

    @Test
    public void testIsValidUserPasswordPositive() {
        String pass = "passw#%:;=’@&$|asdfas addf";
        Assert.assertTrue(FieldDataValidator.isValidUserPassword(pass));
    }

    @Test
    public void testIsValidUserPasswordNegativeNullParameter() {
        Assert.assertFalse(FieldDataValidator.validateRequestParameter(null));
    }

    @Test
    public void testIsValidUserPasswordNegativeWrongParameter() {
        String pass = "passw#%:;=’@&$|asdfas <>addf";
        Assert.assertFalse(FieldDataValidator.validateRequestParameter(pass));
    }

    @Test
    public void testIsValidUserPasswordNegativeWrongLength() {
        String pass = "pass";
        Assert.assertFalse(FieldDataValidator.isValidUserPassword(pass));
    }

    @Test
    public void testValidateRequestParameterPositive() {
        String param = "good";
        Assert.assertTrue(FieldDataValidator.validateRequestParameter(param));
    }

    @Test
    public void testValidateRequestParameterNegativeNullParameter() {
        Assert.assertFalse(FieldDataValidator.validateRequestParameter(null));
    }

    @Test
    public void testValidateRequestParameterNegativeWrongParameter1() {
        String param = "wrong<>";
        Assert.assertFalse(FieldDataValidator.validateRequestParameter(param));
    }

    @Test
    public void testValidateRequestParameterNegativeWrongParameter2() {
        String param = "pass{}";
        Assert.assertFalse(FieldDataValidator.validateRequestParameter(param));
    }

    @Test
    public void testValidateRequestTextAreaParameterPositive() {
        String param = "good";
        Assert.assertTrue(FieldDataValidator.validateRequestTextAreaParameter(param));
    }

    @Test
    public void testValidateRequestTextAreaParameterNegativeNullParameter() {
        Assert.assertFalse(FieldDataValidator.validateRequestTextAreaParameter(null));
    }

    @Test
    public void testValidateRequestTextAreaParameterNegativeWrongParameter() {
        String param = "wrong<>";
        Assert.assertFalse(FieldDataValidator.validateRequestTextAreaParameter(param));
    }
}