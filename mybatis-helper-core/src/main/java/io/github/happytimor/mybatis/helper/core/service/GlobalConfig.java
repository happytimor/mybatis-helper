package io.github.happytimor.mybatis.helper.core.service;

/**
 * @author chenpeng
 */
public class GlobalConfig {
    private static IdentifierGenerator identifierGenerator;

    public static void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
        GlobalConfig.identifierGenerator = identifierGenerator;
    }

    public static IdentifierGenerator getIdentifierGenerator() {
        return identifierGenerator;
    }
}
