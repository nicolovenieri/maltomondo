package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import com.maltomondo.maltomondo.model.dao.ProdottoDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.dao.exception.NotAvaibleProductException;
import com.maltomondo.maltomondo.model.dao.exception.OptimisticLockException;
import com.maltomondo.maltomondo.model.mo.Prodotto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAOMySQLJDBCImpl implements ProdottoDAO {
    Connection conn;

    public ProdottoDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Prodotto create(String Nome, String Sede_Acquisto, String Descrizione, BigDecimal Prezzo, int Quantità_disp, String Categoria, Boolean Push) throws DuplicatedObjectException {
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(Nome);
        prodotto.setSede(Sede_Acquisto);
        prodotto.setDescrizione(Descrizione);
        prodotto.setPrezzo(Prezzo);
        prodotto.setQuantità_disponibile(Quantità_disp);
        prodotto.setCategoria(Categoria);
        prodotto.setPush(Push);

        try {
            String sql = " SELECT ID_amuleto  FROM prodotto  WHERE  Deleted =0 AND Nome=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, prodotto.getNome());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("ProdottoDAOJDBCImpl.create: Tentativo di inserimento di un prodotto già esistente.");
            } else {
                sql = " INSERT INTO prodotto    ( Nome,     Sede_Acquisto,     Descrizione,     Prezzo,     Quantità_disp,     Categoria,     Push   )  VALUES (?,?,?,?,?,?,?)";
                ps = this.conn.prepareStatement(sql);
                int i = 1;
                ps.setString(i++, prodotto.getNome());
                ps.setString(i++, prodotto.getSede());
                ps.setString(i++, prodotto.getDescrizione());
                ps.setBigDecimal(i++, prodotto.getPrezzo());
                ps.setInt(i++, prodotto.getQuantità_disponibile());
                ps.setString(i++, prodotto.getCategoria());
                ps.setBoolean(i++, prodotto.isPush());
                ps.executeUpdate();
                return prodotto;
            }
        } catch (SQLException var14) {
            throw new RuntimeException(var14);
        }
    }

    public void update(Prodotto prodotto) throws DuplicatedObjectException {
    }

    public void delete(Prodotto prodotto) {
    }

    public Prodotto findByProdottoId(Long ID_amuleto) {
        Prodotto product = null;

        try {
            String sql = " SELECT * FROM PRODOTTO  WHERE    ID_amuleto = ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_amuleto);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                product = read(resultSet);
            }

            resultSet.close();
            ps.close();
            return product;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public List<Prodotto> findByCategoryANDSearchString(String category, String searchString) {
        ArrayList<Prodotto> prodotti = new ArrayList();

        try {
            String sql = " SELECT * FROM prodotto  WHERE    Deleted=0 AND Blocked=0";
            if (category != null) {
                sql = sql + " AND Categoria = ? ";
            }

            if (searchString != null) {
                sql = sql + " AND ( INSTR(Nome,?)>0 ";
                sql = sql + " OR INSTR(Sede_acquisto,?)>0 )";
            }

            sql = sql + " ORDER BY Nome;";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            if (category != null) {
                ps.setString(i++, category);
            }

            if (searchString != null) {
                ps.setString(i++, searchString);
                ps.setString(i++, searchString);
            }

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Prodotto prodotto = read(resultSet);
                prodotti.add(prodotto);
            }

            resultSet.close();
            ps.close();
            return prodotti;
        } catch (SQLException var9) {
            throw new RuntimeException(var9);
        }
    }

    public int checkAndDecreaseQuantity(Long ID_amuleto, int quantity) throws NotAvaibleProductException, OptimisticLockException {
        Prodotto prodotto = null;

        try {
            String sql = " SELECT * FROM PRODOTTO  WHERE    Deleted=0 AND Blocked=0 AND ID_amuleto=? AND Quantità_disp>=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_amuleto);
            ps.setInt(2, quantity);
            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            for(exist = false; resultSet.next(); exist = true) {
                prodotto = read(resultSet);
            }

            resultSet.close();
            if (!exist) {
                throw new NotAvaibleProductException("ProdottoDAOJDBCImpl.decreaseQuantity: La quantità voluta del prodotto selezionato è minore della quantità disponibile, oppure il prodotto è stato eliminato o bloccato.");
            }

            sql = " UPDATE Prodotto  SET Quantità_disp=?, version=?  WHERE  ID_amuleto=? AND Blocked=0 AND Deleted=0 AND version=?";
            ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i++, prodotto.getQuantità_disponibile() - quantity);
            ps.setLong(i++, prodotto.getVersion() + 1L);
            ps.setLong(i++, ID_amuleto);
            ps.setLong(i++, prodotto.getVersion());
            int rowsAffected = ps.executeUpdate();
            ps.close();
            if (rowsAffected <= 0) {
                System.out.println("Update did not affect any rows.");
                throw new OptimisticLockException("Eccezione lock ottimistico aggiornamento quantità");
            }

            System.out.println("Update successful. Rows affected: " + rowsAffected);
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }

        return prodotto.getQuantità_disponibile() - quantity;
    }

    public void checkAvaibleQuantity(Long ID_amuleto, int quantity) throws NotAvaibleProductException {
        Prodotto prodotto = null;

        try {
            String sql = " SELECT Quantità_disp FROM PRODOTTO  WHERE    Deleted=0 AND Blocked=0 AND ID_amuleto=? AND Quantità_disp>=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_amuleto);
            ps.setInt(2, quantity);
            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            for(exist = false; resultSet.next(); exist = true) {
                prodotto = read(resultSet);
            }

            resultSet.close();
            if (!exist) {
                throw new NotAvaibleProductException("ProdottoDAOJDBCImpl.decreaseQuantity: La quantità voluta del prodotto selezionato è minore della quantità disponibile, oppure il prodotto è stato eliminato o bloccato.");
            }
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
    }

    public Boolean isAvaible(Prodotto product, int quantity) {
        Prodotto prodotto = null;

        try {
            String sql = " SELECT Quantità_disp FROM PRODOTTO  WHERE    Deleted=0 AND Blocked=0 AND ID_amuleto=? AND Quantità_disp>=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, product.getId_amuleto());
            ps.setInt(2, quantity);
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            return exist;
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
    }

    public void modify(Long ID_prodotto, String nome, String sede, String descrizione, BigDecimal prezzo, int quantita, String categoria, Boolean deleted, Boolean push, Boolean blocked) {
        Prodotto prodotto = null;

        try {
            String sql = " UPDATE PRODOTTO  SET Nome=?, Sede_Acquisto=?, Descrizione=?, Prezzo=?, Quantità_disp=?, Categoria=?, Deleted=?, Push=?, Blocked=?  WHERE  ID_amuleto=? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, nome);
            ps.setString(i++, sede);
            ps.setString(i++, descrizione);
            ps.setBigDecimal(i++, prezzo);
            ps.setInt(i++, quantita);
            ps.setString(i++, categoria);
            ps.setBoolean(i++, deleted);
            ps.setBoolean(i++, push);
            ps.setBoolean(i++, blocked);
            ps.setLong(i++, ID_prodotto);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var15) {
            throw new RuntimeException(var15);
        }
    }

    public List<Prodotto> findPushProducts() {
        ArrayList<Prodotto> prodotti = new ArrayList();

        try {
            String sql = " SELECT * FROM prodotto  WHERE    Deleted=0 AND Blocked=0 AND Push=1 AND Quantità_disp>=1";
            sql = sql + " ORDER BY Nome;";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                Prodotto prodotto = read(resultSet);
                prodotti.add(prodotto);
            }

            resultSet.close();
            ps.close();
            return prodotti;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    static Prodotto read(ResultSet rs) {
        Prodotto prodotto = new Prodotto();

        try {
            prodotto.setId_amuleto(rs.getLong("Id_amuleto"));
        } catch (SQLException var14) {
        }

        try {
            prodotto.setNome(rs.getString("Nome"));
        } catch (SQLException var13) {
        }

        try {
            prodotto.setSede(rs.getString("Sede_Acquisto"));
        } catch (SQLException var12) {
        }

        try {
            prodotto.setDescrizione(rs.getString("Descrizione"));
        } catch (SQLException var11) {
        }

        try {
            prodotto.setPrezzo(rs.getBigDecimal("Prezzo"));
        } catch (SQLException var10) {
        }

        try {
            prodotto.setQuantità_disponibile(rs.getInt("Quantità_disp"));
        } catch (SQLException var9) {
        }

        try {
            prodotto.setCategoria(rs.getString("Categoria"));
        } catch (SQLException var8) {
        }

        try {
            prodotto.setDeleted(rs.getBoolean("Deleted"));
        } catch (SQLException var7) {
        }

        try {
            prodotto.setPush(rs.getBoolean("Push"));
        } catch (SQLException var6) {
        }

        try {
            prodotto.setBlocked(rs.getBoolean("Blocked"));
        } catch (SQLException var5) {
        }

        try {
            prodotto.setVersion(rs.getLong("Version"));
        } catch (SQLException var4) {
        }

        try {
            prodotto.setImg_path(rs.getString("img_path"));
        } catch (SQLException var3) {
        }

        return prodotto;
    }
}

