
import config.ApplicationConfig;
import dao.RoleDao;
import dto.Role;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;


public class DataSourceTest {
    @Test
    public void dbAccess(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DataSource ds = ac.getBean(DataSource.class); //data source 객체 받아오기
        try(Connection conn = ds.getConnection();) {
            Assert.assertNotNull(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void selectAll(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        RoleDao dao = ac.getBean(RoleDao.class);
        List<Role> list = dao.selectAll();
        for(Role role : list){
            System.out.println(role);
        }
        assertTrue(list.size() == 5);
    }

    @Test
    @Transactional
    public void insert(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        RoleDao dao = ac.getBean(RoleDao.class);
        Role role = new Role();
        role.setDescription("test3");
        role.setRoleId(700);
        dao.insert(role);
        Role res = dao.selectById(700);
        assertEquals(700,res.getRoleId());
        assertEquals("test3",res.getDescription());
    }

    @Test
    public void update(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        RoleDao dao = ac.getBean(RoleDao.class);
        Role role = new Role();
        role.setDescription("staff2");
        role.setRoleId(3);
        dao.insert(role);
        Role res = dao.selectById(3);
        assertEquals(3,res.getRoleId());
        assertEquals("staff2",res.getDescription());
    }
}
