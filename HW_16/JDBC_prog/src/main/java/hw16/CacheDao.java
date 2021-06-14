package hw16;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class CacheDao {
    final String INSERT_QUERY = "INSERT INTO CacheTable VALUES(?, ?);";
    final String SELECT_QUERY = "SELECT result FROM CacheTable WHERE arg=?";

    List GetResult(int arg){
        try {
            PreparedStatement prepareStatement = DataSourceHelper.connection().prepareStatement(SELECT_QUERY);
            prepareStatement.setInt(1, arg);
            ResultSet result = prepareStatement.executeQuery();
            if(result.next()){
                Array resAr = result.getArray("result");
                Object[] intArr = (Object[]) resAr.getArray();
                //Integer[] intArr1 = (Integer[]) resAr.getArray();
                System.out.print("from cache:");
                return  Arrays.asList(intArr);
            }
            else{
                return  null;
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    boolean PutResult(int arg, List<Integer> result){
        try {
            PreparedStatement prepareStatement = DataSourceHelper.connection().prepareStatement(INSERT_QUERY);
            prepareStatement.setInt(1, arg);
            Array ar = DataSourceHelper.connection().createArrayOf("INT", result.toArray());
            prepareStatement.setArray(2, ar);
            return prepareStatement.execute();

        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}
