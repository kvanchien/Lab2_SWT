/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Asus
 */
public class ResetDAO {
    public void resetDatabase() throws Exception {
        try {
            DBContext db = new DBContext();
            Logger logger = Logger.getLogger(ResetDAO.class.getName());
            try (Connection con = db.getConnection()) {
                String sql = "{call reset_database}";
                try (CallableStatement st = con.prepareCall(sql)) {
                    if (st.executeUpdate() != 1){
                        logger.log(Level.WARNING, "ERROR RESETING");
                    }
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ResetDAO.class.getName());
            logger.log(Level.WARNING, "ERROR");
        }
    }
}
