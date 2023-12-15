package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.maltomondo.maltomondo.model.dao.PagamentoDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Ordine;
import com.maltomondo.maltomondo.model.mo.Pagamento;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagamentoDAOMySQLJDBCImpl implements PagamentoDAO {
    Connection conn;

    public PagamentoDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Pagamento create(BigDecimal Importo, Ordine Order, Double NumeroCarta, int CVV, String Name, String ScadenzaCarta) throws DuplicatedObjectException {
        Pagamento pagamento = new Pagamento();
        pagamento.setImporto(Importo);
        pagamento.setOrdine(Order);
        pagamento.setNumeroCarta(NumeroCarta);
        pagamento.setCVV(CVV);
        pagamento.setName(Name);
        pagamento.setScadenzaCarta(ScadenzaCarta);

        try {
            String sql = " SELECT *  FROM PAGAMENTO  WHERE  Deleted ='N' AND ID_order=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, Order.getID_ordine());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("PagamentoDAOJDBCImpl.create: Tentativo di inserimento di un pagamento già associato ad un ordine.");
            } else {
                sql = " INSERT INTO PAGAMENTO    ( Importo,     ID_order,     NumeroCarta,     CVV,     Nome,     ScadenzaCarta   )  VALUES (?,?,?,?,?,?)";
                ps = this.conn.prepareStatement(sql);
                int i = 1;
                ps.setBigDecimal(i++, Importo);
                ps.setLong(i++, Order.getID_ordine());
                ps.setDouble(i++, NumeroCarta);
                ps.setInt(i++, CVV);
                ps.setString(i++, Name);
                ps.setString(i++, ScadenzaCarta);
                ps.executeUpdate();
                return pagamento;
            }
        } catch (SQLException var13) {
            throw new RuntimeException(var13);
        }
    }

    public Pagamento findbyOrderID(Long ID_order) {
        Pagamento pagamento = null;

        try {
            String sql = " SELECT *  FROM PAGAMENTO  WHERE  Deleted ='N' AND ID_order=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_order);

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); pagamento = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            return pagamento;
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    Pagamento read(ResultSet rs) {
        Pagamento pagamento = new Pagamento();
        Ordine ordine = new Ordine();
        pagamento.setOrdine(ordine);

        try {
            pagamento.setImporto(rs.getBigDecimal("Importo"));
        } catch (SQLException var11) {
        }

        try {
            ordine.setData_ordine(rs.getDate("Data_ordine"));
        } catch (SQLException var10) {
        }

        try {
            pagamento.getOrdine().setID_ordine(rs.getLong("ID_order"));
        } catch (SQLException var9) {
        }

        try {
            pagamento.setNumeroCarta(rs.getDouble("NumeroCarta"));
        } catch (SQLException var8) {
        }

        try {
            pagamento.setCVV(rs.getInt("CVV"));
        } catch (SQLException var7) {
        }

        try {
            pagamento.setName(rs.getString("Nome"));
        } catch (SQLException var6) {
        }

        try {
            pagamento.setScadenzaCarta(rs.getString("ScadenzaCarta"));
        } catch (SQLException var5) {
        }

        return pagamento;
    }
}


