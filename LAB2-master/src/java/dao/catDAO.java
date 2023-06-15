package dao;

import dao.DBContext;
import java.util.ArrayList;
import model.Category;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inspiron
 */
public class catDAO {

    public HashMap<Integer, Category> getAllCategories() throws Exception {
        HashMap<Integer, Category> categoryList = new HashMap<>();

        try (Connection con = new DBContext().getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Category")) {

            while (rs.next()) {
                Category cat = new Category(rs.getInt("Cat_id"), rs.getNString("Cat_name"), rs.getNString("Cat_description"));
                categoryList.put(cat.getId(), cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    public Category getCategory(int id) throws Exception {
        Category cat = null;

        try (Connection con = new DBContext().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM Category WHERE Cat_id = ?");) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cat = new Category(id, rs.getNString("Cat_name"), rs.getNString("Cat_description"));
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Category.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }

        return cat;
    }

    public boolean insertCategory(Category cat) throws Exception {
        try (Connection con = new DBContext().getConnection(); CallableStatement call = con.prepareCall("{call insertCate(?,?)}");) {
            call.setNString(1, cat.getName());
            call.setNString(2, cat.getDes());
            int affectedRows = call.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Category.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }
        return false;
    }

    public boolean deleteCategory(int id) throws Exception {
        try (Connection con = new DBContext().getConnection(); CallableStatement call = con.prepareCall("{call delCate(?)}")) {
            call.setInt(1, id);
            int affectedRows = call.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Category.class.getName());
            logger.log(Level.WARNING, e.getMessage());
        }
        return false;
    }

}
