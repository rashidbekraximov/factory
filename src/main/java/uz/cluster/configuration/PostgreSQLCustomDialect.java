package uz.cluster.configuration;

import org.hibernate.dialect.PostgreSQL10Dialect;
import uz.cluster.db.types.Nls;

import java.sql.Types;

public class PostgreSQLCustomDialect extends PostgreSQL10Dialect {
    public PostgreSQLCustomDialect() {
        super();
        this.registerHibernateType(
                Types.STRUCT, Nls.class.getName()
        );
//        this.registerHibernateType();
    }
}
