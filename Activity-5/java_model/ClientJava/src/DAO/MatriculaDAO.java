package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Matricula;
import Model.Course;
import Model.Disciplina;
import Model.Student;

public class MatriculaDAO extends DBConnection {
	
    private Connection conn;
    private final String sqlInsert = "INSERT INTO Matricula(ra, code, ano, semestre, nota, faltas) VALUES (?,?,?,?,?,?)";
    private final String sqlUpdate = "UPDATE Matricula SET code= ?, ano= ?, semestre= ?, nota= ?, faltas=?  WHERE ra = ?";
    private final String sqlRemove = "DELETE FROM Matricula WHERE ra = ?";
    private final String sqlList = "SELECT ra, code, ano, semestre, nota, faltas FROM Matricula ORDER BY ano";
    private final String sqlList2 = "SELECT ra, code, ano, semestre, nota, faltas FROM Matricula ORDER BY semestre";
    private final String sqlFind = "SELECT ra, code, ano, semestre, nota, faltas FROM Matricula WHERE ra = ?";

    public void insert(Matricula mat) throws SQLException {
        PreparedStatement ps = null;
        try {
            conn = connect();
            int[] pos;
            ps = conn.prepareStatement(sqlInsert);
          //  ps.setInt(pos, mat.getFaltas());
          //  ps.setFloat(pos, mat.getNota());
            ps.execute();
        } finally {
            ps.close();
            close(conn);
        }
    }

    public void update(Matricula mat) throws SQLException {
        PreparedStatement ps = null;
        try {
            conn = connect();
            int[] pos;
            ps = conn.prepareStatement(sqlUpdate);
          //  ps.setInt(pos, mat.getFaltas());
          //  ps.setFloat(pos, mat.getNota());
            ps.execute();
        } finally {
            ps.close();
            close(conn);
        }
    }

    public void remove(String ra) throws SQLException {
        PreparedStatement ps = null;
        try {
            conn = connect();
            ps = conn.prepareStatement(sqlRemove);
          //  ps.setStudent(1, ra);
            ps.execute();
        } finally {
            ps.close();
            close(conn);
        }
    }

    public ArrayList<Matricula> list() throws SQLException, ClassNotFoundException, IOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connect();
            ps = conn.prepareStatement(sqlList);
            rs = ps.executeQuery();
            ArrayList<Matricula> list = new ArrayList<>();
            Matricula matricula;

            
            while (rs.next()) {
                Matricula = new Matricula();
                matricula.setRA(rs.getStudent("ra"));
                matricula.setCode(rs.getDisciplina("code"));
                matricula.setAno(rs.getInt("ano"));
                matricula.setSemestre(rs.getInt("semestre"));
                matricula.setNota(rs.getFloat("nota"));
                matricula.setFaltas(rs.getFaltas("faltas"));
                list.add(matricula);
            }
            return list;
        } finally {
            rs.close();
            ps.close();
            close(conn);
        }
    }

    public Matricula find(Student ra) throws SQLException, ClassNotFoundException, IOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connect();
            ps = conn.prepareStatement(sqlFind);
            ps.setStudent(1, ra);
            rs = ps.executeQuery();
            Matricula matricula = null;
            if (rs.next()) {
            	Matricula = new Matricula();
                matricula.setRA(rs.getStudent("ra"));
                matricula.setCode(rs.getDisciplina("code"));
                matricula.setAno(rs.getInt("ano"));
                matricula.setSemestre(rs.getInt("semestre"));
                matricula.setNota(rs.getFloat("nota"));
                matricula.setFaltas(rs.getFaltas("faltas"));
                list.add(matricula);
            }
            return matricula;
        } finally {
            rs.close();
            ps.close();
            close(conn);
        }
    }

}
