package com.maltomondo.maltomondo.model.dao.mySQLJDBCImpl;

import com.maltomondo.maltomondo.model.dao.CartItemDAO;
import com.maltomondo.maltomondo.model.dao.exception.DuplicatedObjectException;
import com.maltomondo.maltomondo.model.mo.Carrello;
import com.maltomondo.maltomondo.model.mo.CartItem;
import com.maltomondo.maltomondo.model.mo.Prodotto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemDAOMySQLJDBCImpl implements CartItemDAO {
    Connection conn;

    public CartItemDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public CartItem create(Carrello cart, Prodotto product, int Quantità) throws DuplicatedObjectException {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProdotto(product);
        cartItem.setQuantità(Quantità);

        try {
            String sql = " SELECT ID_car  FROM CARTITEM  WHERE  Deleted ='N' AND ID_car=? AND ID_amulet=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, cartItem.getCart().getId_carrello());
            ps.setLong(i++, cartItem.getProdotto().getId_amuleto());
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("CartItemDAOJDBCImpl.create: Tentativo di inserimento di un cart-item già esistente.");
            } else {
                sql = " INSERT INTO CARTITEM    ( ID_amulet,     ID_car,     Quantità   )  VALUES (?,?,?)";
                ps = this.conn.prepareStatement(sql);
                i = 1;
                i = i + 1;
                ps.setLong(i, cartItem.getProdotto().getId_amuleto());
                ps.setLong(i++, cartItem.getCart().getId_carrello());
                ps.setInt(i++, cartItem.getQuantità());
                ps.executeUpdate();
                return cartItem;
            }
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }

    public void create(Carrello cart, Long ID_amuleto, int Quantità) throws DuplicatedObjectException {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantità(Quantità);

        try {
            String sql = " SELECT ID_car  FROM CARTITEM  WHERE  Deleted ='N' AND ID_car=? AND ID_amulet=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, cartItem.getCart().getId_carrello());
            ps.setLong(i++, ID_amuleto);
            ResultSet resultSet = ps.executeQuery();
            boolean exist = resultSet.next();
            resultSet.close();
            if (exist) {
                throw new DuplicatedObjectException("CartItemDAOJDBCImpl.create: Tentativo di inserimento di un cart-item già esistente.");
            } else {
                sql = " INSERT INTO CARTITEM    ( ID_amulet,     ID_car,     Quantità   )  VALUES (?,?,?)";
                ps = this.conn.prepareStatement(sql);
                i = 1;
                i = i + 1;
                ps.setLong(i, ID_amuleto);
                ps.setLong(i++, cartItem.getCart().getId_carrello());
                ps.setInt(i++, cartItem.getQuantità());
                ps.executeUpdate();
            }
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }

    public void modifyQuantity(Long ID_amuleto, Long ID_carrello, int quantità) {
        try {
            String sql = " UPDATE CARTITEM  SET Quantità=? WHERE  ID_car=? AND ID_amulet=? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i++, quantità);
            ps.setLong(i++, ID_carrello);
            ps.setLong(i++, ID_amuleto);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    public void modifyQuantityBySum(CartItem item, int quantità) {
        try {
            String sql = " UPDATE CARTITEM  SET Quantità=? WHERE  ID_car=? AND ID_amulet=? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i++, quantità + item.getQuantità());
            ps.setLong(i++, item.getCart().getId_carrello());
            ps.setLong(i++, item.getProdotto().getId_amuleto());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public void deleteItem(Long ID_carrello, Long ID_amuleto) {
        try {
            String sql = " DELETE FROM CARTITEM  WHERE  ID_car=? AND ID_amulet=? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, ID_carrello);
            ps.setLong(i++, ID_amuleto);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var6) {
            throw new RuntimeException(var6);
        }
    }

    public Map<CartItem, Prodotto> findCartItems(Long ID_cart) {
        Prodotto prodotto = null;
        Map<CartItem, Prodotto> cartItems = new HashMap();

        try {
            String sql = " SELECT * FROM CartItem JOIN PRODOTTO ON ID_amulet=ID_amuleto  WHERE    CartItem.deleted='N'AND ID_car=? ORDER BY PRODOTTO.Nome";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_cart);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                prodotto = ProdottoDAOMySQLJDBCImpl.read(resultSet);
                CartItem cartItem = this.read(resultSet);
                cartItems.put(cartItem, prodotto);
            }

            resultSet.close();
            ps.close();
            return cartItems;
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
    }

    public Map<CartItem, Prodotto> findOrderItems(Long ID_order) {
        Prodotto prodotto = null;
        Map<CartItem, Prodotto> cartItems = new HashMap();

        try {
            String sql = " SELECT * FROM CartItem JOIN PRODOTTO ON ID_amulet=ID_amuleto JOIN CARRELLO ON ID_carrello = ID_car JOIN ORDINE ON ID_carrello = ID_cart  WHERE    CartItem.deleted='N'AND ID_ordine=? AND Stato_c='Chiuso' ORDER BY PRODOTTO.Nome";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_order);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                prodotto = ProdottoDAOMySQLJDBCImpl.read(resultSet);
                CartItem cartItem = this.read(resultSet);
                cartItems.put(cartItem, prodotto);
            }

            resultSet.close();
            ps.close();
            return cartItems;
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
    }

    public List<CartItem> getCartItems(Carrello cart) {
        CartItem item = null;
        ArrayList<CartItem> items = new ArrayList();

        try {
            String sql = " SELECT *  FROM CARTITEM  WHERE    ID_car = ?    AND deleted = 'N' ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, cart.getId_carrello());
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                item = this.read(resultSet);
                items.add(item);
            }

            resultSet.close();
            ps.close();
            return items;
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    public CartItem findCartItem(Long ID_cart, Long ID_amulet) {
        CartItem item = null;

        try {
            String sql = " SELECT *  FROM CARTITEM  WHERE    ID_car = ? AND ID_amulet = ?   AND deleted = 'N' ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1, ID_cart);
            ps.setLong(2, ID_amulet);

            ResultSet resultSet;
            for(resultSet = ps.executeQuery(); resultSet.next(); item = this.read(resultSet)) {
            }

            resultSet.close();
            ps.close();
            return item;
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    CartItem read(ResultSet rs) {
        CartItem cartItem = new CartItem();
        Prodotto product = new Prodotto();
        cartItem.setProdotto(product);
        Carrello cart = new Carrello();
        cartItem.setCart(cart);

        try {
            cartItem.getProdotto().setId_amuleto(rs.getLong("ID_amulet"));
        } catch (SQLException var9) {
        }

        try {
            cartItem.getCart().setId_carrello(rs.getLong("ID_car"));
        } catch (SQLException var8) {
        }

        try {
            cartItem.setQuantità(rs.getInt("Quantità"));
        } catch (SQLException var7) {
        }

        try {
            cartItem.setDeleted(rs.getBoolean("deleted"));
        } catch (SQLException var6) {
        }

        return cartItem;
    }
}

