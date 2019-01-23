package foo;

import org.jooq.Converter;

public class PrefixBarConverter implements Converter<String, String> {

    @Override public Class<String> fromType() { return String.class; }
    @Override public Class<String> toType() { return String.class; }
    @Override public String from(String db) {
        return db == null ? null : "Bar" + db;
    }
    @Override public String to(String java) {
        return java == null ? null : java.replaceFirst("^Bar", "");
    }
}
