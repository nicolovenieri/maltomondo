package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.maltomondo.maltomondo.model.dao.UtenteDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {
    private final String COUNTER_ID = "id_utente";
    Connection conn;

    public UtenteDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Utente create(Long Id_utente, String Nome, String Cognome, String Email, String Indirizzo, String Città, String Stato, String Password, Boolean Admin, int CAP) throws DuplicatedObjectException {
        Utente utente = new Utente();
        utente.setNome(Nome);
        utente.setCognome(Cognome);
        utente.setEmail(Email);
        utente.setIndirizzo(Indirizzo != null ? Indirizzo : ""); // Imposta una stringa vuota se Indirizzo è null
        utente.setCittà(Città != null ? Città : ""); // Imposta una stringa vuota se Città è null
        utente.setStato(Stato != null ? Stato : "");
        utente.setPassword(Password);
        utente.setCAP(CAP);

        try {
            String sql = " SELECT ID_utente  FROM utente  WHERE  Deleted ='0' AND Email=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, utente.getEmail());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("UtenteDAOJDBCImpl.create: Tentativo di inserimento di un utente già esistente.");
            } else {
                sql = " INSERT INTO utente    ( Nome,     Cognome,     Email,     Indirizzo,     Città,     Stato,     Passwd,     CAP)  VALUES (?,?,?,?,?,?,?,?)";
                ps = this.conn.prepareStatement(sql);
                int i = 1;
                ps.setString(i++, utente.getNome());
                ps.setString(i++, utente.getCognome());
                ps.setString(i++, utente.getEmail());
                ps.setString(i++, utente.getIndirizzo());
                ps.setString(i++, utente.getCittà());
                ps.setString(i++, utente.getStato());
                ps.setString(i++, utente.getPassword());
                ps.setInt(i++, utente.getCAP());
                ps.executeUpdate();
                return utente;
            }
        } catch (SQLException var17) {
            throw new RuntimeException(var17);
        }
    }

    public void update(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteUser(Utente utente) {
        try {
            String sql = " UPDATE UTENTE  SET Deleted=1  WHERE  ID_utente=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, utente.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Utente findLoggedUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void block(Utente utente) {
        try {
            String sql = " UPDATE UTENTE  SET Blocked=1  WHERE  ID_utente=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, utente.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void unblock(Utente utente) {
        try {
            String sql = " UPDATE UTENTE  SET Blocked=0  WHERE  ID_utente=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, utente.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void setAdmin(Utente utente) {
        try {
            String sql = " UPDATE UTENTE  SET Admin=1  WHERE  ID_utente=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, utente.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void unsetAdmin(Utente utente) {
        try {
            String sql = " UPDATE UTENTE  SET Admin=0  WHERE  ID_utente=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, utente.getId_utente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Utente findByUserId(Long id_utente) {
        Utente utente = null;

        try {
            String sql = " SELECT *    FROM utente  WHERE    ID_utente = ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, id_utente);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                utente = this.read(resultSet);
            }

            resultSet.close();
            ps.close();
            return utente;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public Utente findByEmail(String Email) {
        Utente utente = null;

        try {
            String sql = " SELECT *    FROM utente  WHERE    Email = ? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, Email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                utente = this.read(resultSet);
            }

            resultSet.close();
            ps.close();
            return utente;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public List<Utente> findAllAndSearchString(String searchString) {
        ArrayList<Utente> utenti = new ArrayList();

        try {
            String sql = " SELECT * FROM utente  WHERE  (ID_utente>0) ";
            if (searchString != null) {
                sql = sql + " AND ( INSTR(Nome,?)>0 ";
                sql = sql + " OR INSTR(Cognome,?)>0 ";
                sql = sql + " OR INSTR(Email,?)>0 ";
                sql = sql + "OR INSTR(ID_utente,?)>0 )";
            }

            sql = sql + " ORDER BY ID_utente";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            if (searchString != null) {
                int i = 1;
                ps.setString(i++, searchString);
                ps.setString(i++, searchString);
                ps.setString(i++, searchString);
                ps.setString(i++, searchString);
            }

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Utente utente = this.read(resultSet);
                utenti.add(utente);
            }

            resultSet.close();
            ps.close();
            return utenti;
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    Utente read(ResultSet rs) {
        Utente utente = new Utente();

        try {
            utente.setId_utente(rs.getLong("Id_utente"));
        } catch (SQLException var15) {
        }

        try {
            utente.setNome(rs.getString("Nome"));
        } catch (SQLException var14) {
        }

        try {
            utente.setPassword(rs.getString("Passwd"));
        } catch (SQLException var13) {
        }

        try {
            utente.setCognome(rs.getString("Cognome"));
        } catch (SQLException var12) {
        }

        try {
            utente.setEmail(rs.getString("Email"));
        } catch (SQLException var11) {
        }

        try {
            utente.setAdmin(rs.getBoolean("Admin"));
        } catch (SQLException var10) {
        }

        try {
            utente.setDeleted(rs.getString("Deleted").equals("1"));
        } catch (SQLException var9) {
        }

        try {
            utente.setIndirizzo(rs.getString("Indirizzo"));
        } catch (SQLException var8) {
        }

        try {
            utente.setStato(rs.getString("Stato"));
        } catch (SQLException var7) {
        }

        try {
            utente.setCittà(rs.getString("Città"));
        } catch (SQLException var6) {
        }

        try {
            utente.setBlocked(rs.getString("Blocked").equals("1"));
        } catch (SQLException var5) {
        }

        try {
            utente.setCAP(rs.getInt("CAP"));
        } catch (SQLException var4) {
        }

        return utente;
    }
}

