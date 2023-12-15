package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.maltomondo.maltomondo.model.dao.OrdineDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdineDAOMySQLJDBCImpl implements OrdineDAO {
    Connection conn;

    public OrdineDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Ordine create(Date Data_ordine, Carrello carrello, Utente utente, String Indirizzo_consegna, String Città_consegna, int CAP_consegna, String Nazione_consegna) throws DuplicatedObjectException {
        Ordine ordine = new Ordine();
        ordine.setData_ordine(Data_ordine);
        ordine.setCarrello(carrello);
        ordine.setUtente(utente);
        ordine.setIndirizzo_consegna(Indirizzo_consegna);
        ordine.setCittà_consegna(Città_consegna);
        ordine.setCAP_consegna(CAP_consegna);
        ordine.setNazione_consegna(Nazione_consegna);

        try {
            String sql = " SELECT ID_ordine  FROM ORDINE  WHERE  Deleted ='N' AND ID_cart=? AND (Stato_o='In sospeso' OR Stato_o='Completato') ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, carrello.getId_carrello());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("OrdineDAOJDBCImpl.create: Tentativo di inserimento di un ordine già associato ad un carrello.");
            } else {
                sql = " INSERT INTO ORDINE    ( Data_ordine,     ID_cart,     ID_utente,     Indirizzo_consegna,     Città_consegna,     CAP_consegna,     Nazione_consegna   )  VALUES (?,?,?,?,?,?,?)";
                ps = this.conn.prepareStatement(sql);
                int i = 1;
                ps.setDate(i++, (java.sql.Date)ordine.getData_ordine());
                ps.setLong(i++, carrello.getId_carrello());
                ps.setLong(i++, utente.getId_utente());
                ps.setString(i++, ordine.getIndirizzo_consegna());
                ps.setString(i++, ordine.getCittà_consegna());
                ps.setInt(i++, ordine.getCAP_consegna());
                ps.setString(i++, ordine.getNazione_consegna());
                ps.executeUpdate();
                sql = " SELECT ID_ordine  FROM ORDINE  WHERE  Deleted ='N' AND ID_cart=? AND (Stato_o='In sospeso' OR Stato_o='Completato') ";
                ps = this.conn.prepareStatement(sql);
                ps.setLong(1, carrello.getId_carrello());

                for(resultSet = ps.executeQuery(); resultSet.next(); ordine = this.read(resultSet)) {
                }

                resultSet.close();
                ps.close();
                return ordine;
            }
        } catch (SQLException var14) {
            throw new RuntimeException(var14);
        }
    }

    public void update(Ordine ordine) {
    }

    public void delete(Ordine ordine) {
        try {
            String sql = " UPDATE ORDINE  SET Deleted=1  WHERE  ID_ordine=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void permanentlydelete(Ordine ordine) {
        try {
            String sql = " DELETE FROM ORDINE  WHERE  ID_ordine=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void setConsegnato(Ordine ordine) {
        try {
            String sql = " UPDATE ORDINE  SET Consegnato=1  WHERE  ID_ordine=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void setStatoCompletato(Ordine ordine) {
        try {
            String sql = " UPDATE ORDINE  SET Stato_o='Completato'  WHERE  ID_ordine=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Ordine setStatoAnnullato(Ordine ordine) {
        Ordine order = null;

        try {
            String sql = " SELECT * FROM ORDINE  WHERE  (ID_ordine=?) ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); order = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            System.out.println("Prima dell'update: ID_oridne: " + order.getID_ordine() + " Stato:" + order.getStato_o());
            sql = " UPDATE ORDINE  SET Stato_o='Annullato'  WHERE  ID_ordine=?";
            System.out.println(ordine.getID_ordine());
            ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());
            ps.executeUpdate();
            ps.close();
            sql = " SELECT * FROM ORDINE  WHERE  (ID_ordine=?) ";
            ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ordine.getID_ordine());

            for(resultSet = ps.executeQuery(); resultSet.next(); order = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            System.out.println("\nDopo dell'update: ID_oridne: " + order.getID_ordine() + " Stato:" + order.getStato_o());
            return order;
        } catch (SQLException var6) {
            System.out.println("setStatoAnnullato Exception");
            throw new RuntimeException(var6);
        }
    }

    public Ordine findByOrderId(Long ID_ordine) {
        Ordine ordine = null;

        try {
            String sql = " SELECT *    FROM ORDINE  WHERE    ID_ordine = ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_ordine);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ordine = this.read(resultSet);
            }

            resultSet.close();
            ps.close();
            return ordine;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public Ordine findByStatoInSospeso(Utente user) {
        Ordine ordine = null;

        try {
            String sql = " SELECT *    FROM ORDINE  WHERE    Stato_o = 'In sospeso' AND ID_utente=? AND Deleted=0 ORDER BY ID_ordine DESC LIMIT 1";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, user.getId_utente());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                ordine = this.read(resultSet);
            }

            resultSet.close();
            ps.close();
            return ordine;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public List<Ordine> findAllAndSearchString(String searchOrdine) {
        return null;
    }

    public List<Ordine> showPersonalOrders(Long ID_utente) {
        ArrayList<Ordine> ordini = new ArrayList();

        try {
            String sql = " SELECT * FROM ORDINE  WHERE  (ID_utente=?) AND Stato_o='Completato'";
            sql = sql + " ORDER BY ID_ordine DESC";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_utente);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Ordine ordine = this.read(resultSet);
                ordini.add(ordine);
            }

            resultSet.close();
            ps.close();
            return ordini;
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    public Long getLinkedID_Cart(Long ID_ordine) {
        Ordine order = null;

        try {
            String sql = " SELECT ID_cart FROM ORDINE  WHERE  (ID_ordine=?) ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_ordine);

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); order = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            return order.getCarrello().getId_carrello();
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    Ordine read(ResultSet rs) {
        Ordine ordine = new Ordine();
        Carrello cart = new Carrello();
        Utente utente = new Utente();
        ordine.setCarrello(cart);
        ordine.setUtente(utente);

        try {
            ordine.setID_ordine(rs.getLong("ID_ordine"));
        } catch (SQLException var16) {
        }

        try {
            ordine.setData_ordine(rs.getDate("Data_ordine"));
        } catch (SQLException var15) {
        }

        try {
            ordine.setStato_o(rs.getString("Stato_o"));
        } catch (SQLException var14) {
        }

        try {
            ordine.setDeleted(rs.getBoolean("Deleted"));
        } catch (SQLException var13) {
        }

        try {
            ordine.getUtente().setId_utente(rs.getLong("ID_utente"));
        } catch (SQLException var12) {
        }

        try {
            ordine.getCarrello().setId_carrello(rs.getLong("ID_carrello"));
        } catch (SQLException var11) {
        }

        try {
            ordine.setConsegnato(rs.getBoolean("Consegnato"));
        } catch (SQLException var10) {
        }

        try {
            ordine.setIndirizzo_consegna(rs.getString("Indirizzo_consegna"));
        } catch (SQLException var9) {
        }

        try {
            ordine.setCittà_consegna(rs.getString("Città_consegna"));
        } catch (SQLException var8) {
        }

        try {
            ordine.setCAP_consegna(rs.getInt("CAP_consegna"));
        } catch (SQLException var7) {
        }

        try {
            ordine.setNazione_consegna(rs.getString("Nazione_consegna"));
        } catch (SQLException var6) {
        }

        return ordine;
    }
}


