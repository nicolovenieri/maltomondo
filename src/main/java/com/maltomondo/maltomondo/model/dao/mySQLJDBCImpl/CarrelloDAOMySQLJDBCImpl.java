package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;


import com.maltomondo.maltomondo.model.dao.CarrelloDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Utente;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CarrelloDAOMySQLJDBCImpl implements CarrelloDAO {
    Connection conn;

    public CarrelloDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Carrello create(Date DataCreazione, Utente user) throws DuplicatedObjectException {
        Carrello carrello = new Carrello();
        carrello.setData_creazione(DataCreazione);
        carrello.setUtente(user);

        try {
            String sql = " SELECT ID_carrello  FROM CARRELLO  WHERE  Stato_c='Aperto' AND ID_user=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, user.getId_utente());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("CarrelloDAOJDBCImpl.create: Tentativo di inserimento di un carrello vuoto già esistente.");
            } else {
                sql = " INSERT INTO CARRELLO    ( Data_creazione,     ID_user   )  VALUES (?,?)";
                ps = this.conn.prepareStatement(sql);
                int i = 1;
                ps.setDate(i++, (java.sql.Date)carrello.getData_creazione());
                ps.setLong(i++, user.getId_utente());
                ps.executeUpdate();
                return carrello;
            }
        } catch (SQLException var9) {
            throw new RuntimeException(var9);
        }
    }

    public void delete(Carrello cart) {
        try {
            String sql = " DELETE FROM CARRELLO  WHERE  ID_carrello=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, cart.getId_carrello());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void setStatoChiuso(Utente user) {
        try {
            String sql = " UPDATE Carrello  SET Stato_c = 'Chiuso' WHERE  ID_user=? AND Stato_c= 'Aperto' ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, user.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void restoreCart(Carrello cart) {
        try {
            String sql = " UPDATE Carrello  SET Stato_c = 'Aperto' WHERE  ID_carrello=? AND Stato_c= 'Chiuso' ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, cart.getId_carrello());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Carrello findOpenCartByUserId(Long ID_user) {
        Carrello cart = null;

        try {
            String sql = " SELECT * FROM CARRELLO  WHERE    deleted='N'AND ID_user=? AND Stato_c='Aperto'";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_user);

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); cart = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            return cart;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public Carrello findCartByOrder(Ordine order) {
        Carrello cart = null;

        try {
            String sql = " SELECT * FROM ORDINE JOIN CARRELLO ON ID_cart=ID_carrello  WHERE  (ID_ordine=?)";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, order.getID_ordine());

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); cart = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            return cart;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public void UpdateBySumTotal(Carrello cart, BigDecimal prezzo) {
        try {
            String sql = " UPDATE CARRELLO  SET Totale_carrello=?  WHERE  ID_carrello=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setBigDecimal(i++, cart.getTotale_carrello().add(prezzo));
            ps.setLong(i++, cart.getId_carrello());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public void UpdateTotalBySubtract(Carrello cart, BigDecimal prezzo) {
        try {
            String sql = " UPDATE CARRELLO  SET Totale_carrello=?  WHERE  ID_carrello=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setBigDecimal(i++, cart.getTotale_carrello().subtract(prezzo));
            ps.setLong(i++, cart.getId_carrello());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    Carrello read(ResultSet rs) {
        Carrello cart = new Carrello();
        Utente user = new Utente();
        cart.setUtente(user);

        try {
            cart.setId_carrello(rs.getLong("ID_carrello"));
        } catch (SQLException var10) {
        }

        try {
            cart.setStato_c(rs.getString("Stato_c"));
        } catch (SQLException var9) {
        }

        try {
            cart.setData_creazione(rs.getDate("Data_creazione"));
        } catch (SQLException var8) {
        }

        try {
            cart.getUtente().setId_utente(rs.getLong("ID_user"));
        } catch (SQLException var7) {
        }

        try {
            cart.setTotale_carrello(rs.getBigDecimal("Totale_carrello"));
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        try {
            cart.setDeleted(rs.getBoolean("deleted"));
        } catch (SQLException var5) {
        }

        return cart;
    }
}


