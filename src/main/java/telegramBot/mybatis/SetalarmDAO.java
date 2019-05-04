package telegramBot.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

public class SetalarmDAO {

    private SqlSessionFactory sqlSessionFactory = null;
    public SetalarmDAO(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<Setalarm> getOrderStatus(Map map){
        List<Setalarm> list = null;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            list = session.selectList("Setalarm.getOrderStatus",map);
        } finally {
            session.close();
        }
        System.out.println("getOrderStatus(" + map + ")--> "+list);
        return list;
    }




}
