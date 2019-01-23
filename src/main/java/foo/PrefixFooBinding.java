package foo;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;

public class PrefixFooBinding implements Binding<String, String> {

    @Override
    public Converter<String, String> converter() {
        return new Converter<String, String>() {
            @Override public Class<String> fromType() { return String.class; }
            @Override public Class<String> toType() { return String.class; }
            @Override public String from(String db) {
                return db == null ? null : "Foo" + db;
            }
            @Override public String to(String java) {
                return java == null ? null : java.replaceFirst("^Foo", "");
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<String> ctx) throws SQLException {
        // Depending on how you generate your SQL, you may need to explicitly distinguish
        // between jOOQ generating bind variables or inlined literals.
        if (ctx.render().paramType() == ParamType.INLINED)
            ctx.render().visit(DSL.inline(ctx.convert(converter()).value()));
        else
            ctx.render().sql("?");
    }

    @Override
    public void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<String> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), ctx.convert(converter()).value());
    }

    @Override
    public void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}