package com.ibm.grading.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentSubjectDataSource {
	Connection cn;

	private final String USERNAME = "root";
	private final String PASSWORD = "123456";
	private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/grading_db";

	private final String USER_ROLE_ADMIN = "admin";
	private final String TABLE_STUDENT = "user_table";
	private final String TABLE_SUBJECT = "subject_table";
	private final String TABLE_ACCOUNT = "account_table";
	private final String TABLE_GRADE = "grade_table";

	private final String USERNAME_COLUMN = "username";
	private final String PASSWORD_COLUMN = "password";
	private final String ROLE_COLUMN = "role";

	private final String NAME_COLUMN = "name";
	private final String ADDRESS_COLUMN = "address";
	private final String GENDER_COLUMN = "gender";
	private final String STUDENT_ID_COLUMN = "user_id";
	private final String ACCOUNT_ID_COLUMN = "account_id";

	private final String TITLE_COLUMN = "title";
	private final String DESC_COLUMN = "description";
	private final String SUBJECT_ID_COLUMN = "subject_id";

	private final String GRADE_COLUMN = "grade";
	private final String GRADE_ID_COLUMN = "grade_id";

	private final String STUDENT_LIST_QUERY = "SELECT * from " + TABLE_STUDENT + " student INNER JOIN " + TABLE_ACCOUNT
			+ " account ON student." + ACCOUNT_ID_COLUMN + " = account." + ACCOUNT_ID_COLUMN + " WHERE " + ROLE_COLUMN
			+ " != ?";
	
	private final String STUDENT_LIST_QUERY_TABLE = "SELECT * from " + TABLE_STUDENT + " student INNER JOIN " + TABLE_ACCOUNT
			+ " account ON student." + ACCOUNT_ID_COLUMN + " = account." + ACCOUNT_ID_COLUMN + " WHERE " + ROLE_COLUMN
			+ " != ? LIMIT 5 OFFSET ?";
	
	private final String SUBJECT_LIST_QUERY = "SELECT * from " + TABLE_SUBJECT;
	private final String ADD_STUDENT_QUERY = "INSERT INTO " + TABLE_STUDENT + " (" + STUDENT_ID_COLUMN + ", "
			+ ACCOUNT_ID_COLUMN + ", " + NAME_COLUMN + ", " + ADDRESS_COLUMN + ", " + GENDER_COLUMN
			+ ") VALUES (?,?,?,?,?)";

	private final String ADD_ACCOUNT_QUERY = "INSERT INTO " + TABLE_ACCOUNT + " (" + USERNAME_COLUMN + ", "
			+ PASSWORD_COLUMN + "," + ROLE_COLUMN + ") VALUES (?,?,?)";
	private final String ADD_SUBJECT_QUERY = "INSERT INTO " + TABLE_SUBJECT + " (" + TITLE_COLUMN + ", " + DESC_COLUMN
			+ ") VALUES (?,?)";
	private final String VALIDATE_USERNAME = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + USERNAME_COLUMN + " = ?";
	private final String VALIDATE_SUBJECT = "SELECT * FROM " + TABLE_SUBJECT + " WHERE title = ?";
	private final String FIND_STUDENT = "SELECT * FROM " + TABLE_STUDENT + " student INNER JOIN " + TABLE_ACCOUNT
			+ " account ON student." + ACCOUNT_ID_COLUMN + "  = account." + ACCOUNT_ID_COLUMN + " WHERE "
			+ STUDENT_ID_COLUMN + " = ?";
	private final String GRADE_LIST = "SELECT * FROM " + TABLE_GRADE + " as grade INNER JOIN " + TABLE_SUBJECT
			+ " as subject ON grade." + SUBJECT_ID_COLUMN + " = subject." + SUBJECT_ID_COLUMN + " WHERE grade."
			+ STUDENT_ID_COLUMN + " = ?";
	private final String VALIDATE_GRADE = "SELECT * FROM " + TABLE_GRADE + " WHERE " + STUDENT_ID_COLUMN + " = ? AND "
			+ SUBJECT_ID_COLUMN + " = ?";
	private final String ADD_GRADE_QUERY = "INSERT INTO " + TABLE_GRADE + " (" + STUDENT_ID_COLUMN + ", "
			+ SUBJECT_ID_COLUMN + ", " + GRADE_COLUMN + ") VALUES (?,?,?)";

	private final String UPDATE_STUDENT = "UPDATE " + TABLE_STUDENT + " set " + NAME_COLUMN + " = ?, " + ADDRESS_COLUMN
			+ " = ?, " + GENDER_COLUMN + " = ? WHERE  " + STUDENT_ID_COLUMN + " = ?";

	private final String DELETE_ACCOUNT = "DELETE FROM " + TABLE_ACCOUNT + " WHERE " + ACCOUNT_ID_COLUMN + " = ?";
	private final String DELETE_USER = "DELETE FROM " + TABLE_STUDENT + " WHERE " + STUDENT_ID_COLUMN + " = ?";
	private final String DELETE_STUDENT_GRADE = "DELETE FROM " + TABLE_GRADE + " WHERE " + STUDENT_ID_COLUMN + " = ?";
	private final String UPDATE_GRADE = "UPDATE " + TABLE_GRADE + " set " + GRADE_COLUMN + " = ? WHERE "
			+ GRADE_ID_COLUMN + " = ?";
	private final String DELETE_SUBJECT = "DELETE FROM " + TABLE_SUBJECT + " WHERE " + SUBJECT_ID_COLUMN + " = ?";
	private final String DELETE_SUBJECT_GRADE = "DELETE FROM " + TABLE_GRADE + " WHERE " + SUBJECT_ID_COLUMN + " = ?";
	
	private final String UPDATE_SUBJECT = "UPDATE " + TABLE_SUBJECT + " set " + TITLE_COLUMN + " = ?, " + DESC_COLUMN
			+ " = ? WHERE " + SUBJECT_ID_COLUMN + " = ?";
	private final String CHECK_SUBJECT_DUPLICATE = "SELECT * FROM "+TABLE_SUBJECT+" WHERE "+TITLE_COLUMN+" = ? AND "+SUBJECT_ID_COLUMN+" != ?";
	private final String SEARCH_STUDENT = "SELECT * from " + TABLE_STUDENT + " student INNER JOIN " + TABLE_ACCOUNT
			+ " account ON student." + ACCOUNT_ID_COLUMN + " = account." + ACCOUNT_ID_COLUMN + " WHERE " + ROLE_COLUMN
			+ " != ? AND "+NAME_COLUMN+" LIKE ?";;
	private final String SEARCH_SUBJECT = "SELECT * FROM " + TABLE_SUBJECT + " WHERE title LIKE ?";
			
	private PreparedStatement studentListPs;
	private PreparedStatement studentListTablePs;
	private PreparedStatement subjectListPs;
	private PreparedStatement addStudentPs;
	private PreparedStatement addAccountPs;
	private PreparedStatement addSubjectPs;
	private PreparedStatement validateUsernamePs;
	private PreparedStatement validateSubjectPs;
	private PreparedStatement findStudentPs;
	private PreparedStatement gradeListPs;
	private PreparedStatement validateGradePs;
	private PreparedStatement addGradeQueryPs;
	private PreparedStatement updateStudentPs;
	private PreparedStatement deleteAccountPs;
	private PreparedStatement deleteUserPs;
	private PreparedStatement deleteStudentGradePs;
	private PreparedStatement updateGradePs;
	private PreparedStatement deleteSubjectPs;
	private PreparedStatement deleteSubjectGradePs;
	private PreparedStatement updateSubjectPs;
	private PreparedStatement checkSubjectDuplicatePs;
	private PreparedStatement searchStudentPs;
	private PreparedStatement searchSubjectPs;
	
	public boolean open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
			studentListPs = cn.prepareStatement(STUDENT_LIST_QUERY);
			studentListTablePs = cn.prepareStatement(STUDENT_LIST_QUERY_TABLE);
			subjectListPs = cn.prepareStatement(SUBJECT_LIST_QUERY);
			addStudentPs = cn.prepareStatement(ADD_STUDENT_QUERY);
			addAccountPs = cn.prepareStatement(ADD_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
			addSubjectPs = cn.prepareStatement(ADD_SUBJECT_QUERY);
			validateUsernamePs = cn.prepareStatement(VALIDATE_USERNAME);
			validateSubjectPs = cn.prepareStatement(VALIDATE_SUBJECT);
			findStudentPs = cn.prepareStatement(FIND_STUDENT);
			gradeListPs = cn.prepareStatement(GRADE_LIST);
			validateGradePs = cn.prepareStatement(VALIDATE_GRADE);
			addGradeQueryPs = cn.prepareStatement(ADD_GRADE_QUERY);
			updateStudentPs = cn.prepareStatement(UPDATE_STUDENT);
			deleteAccountPs = cn.prepareStatement(DELETE_ACCOUNT);
			deleteUserPs = cn.prepareStatement(DELETE_USER);
			deleteStudentGradePs = cn.prepareStatement(DELETE_STUDENT_GRADE);
			updateGradePs = cn.prepareStatement(UPDATE_GRADE);
			deleteSubjectPs = cn.prepareStatement(DELETE_SUBJECT);
			deleteSubjectGradePs = cn.prepareStatement(DELETE_SUBJECT_GRADE);
			updateSubjectPs = cn.prepareStatement(UPDATE_SUBJECT);
			checkSubjectDuplicatePs = cn.prepareStatement(CHECK_SUBJECT_DUPLICATE);
			searchStudentPs = cn.prepareStatement(SEARCH_STUDENT);
			searchSubjectPs = cn.prepareStatement(SEARCH_SUBJECT);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Can't Connect: " + e.getMessage());
		}
		return false;
	}

	public void close() {
		try {
			if (cn != null) {
				cn.close();
			}
			if (studentListPs != null) {
				studentListPs.close();
			}
			if(studentListTablePs != null) {
				studentListTablePs.close();
			}
			if (subjectListPs != null) {
				studentListPs.close();
			}
			if (addStudentPs != null) {
				addStudentPs.close();
			}
			if (addAccountPs != null) {
				addAccountPs.close();
			}
			if (addSubjectPs != null) {
				addSubjectPs.close();
			}
			if (validateUsernamePs != null) {
				validateUsernamePs.close();
			}
			if (validateSubjectPs != null) {
				validateSubjectPs.close();
			}
			if (findStudentPs != null) {
				findStudentPs.close();
			}
			if (gradeListPs != null) {
				gradeListPs.close();
			}
			if (validateGradePs != null) {
				validateGradePs.close();
			}
			if (updateStudentPs != null) {
				updateStudentPs.close();
			}
			if (deleteAccountPs != null) {
				deleteAccountPs.close();
			}
			if (deleteUserPs != null) {
				deleteUserPs.close();
			}
			if (deleteStudentGradePs != null) {
				deleteStudentGradePs.close();
			}
			if (updateGradePs != null) {
				updateGradePs.close();
			}
			if (deleteSubjectPs != null) {
				deleteSubjectPs.close();
			}
			if (deleteSubjectGradePs != null) {
				deleteSubjectGradePs.close();
			}
			if (updateSubjectPs != null) {
				updateSubjectPs.close();
			}
			if (checkSubjectDuplicatePs != null) {
				checkSubjectDuplicatePs.close();
			}
			if (searchStudentPs != null) {
				searchStudentPs.close();
			}
			if (searchSubjectPs != null) {
				searchSubjectPs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<User> getStudentList() {

		ResultSet rs = null;

		try {
			List<User> students = new ArrayList<User>();
			studentListPs.setString(1, USER_ROLE_ADMIN);
			rs = studentListPs.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					User student = new User();
					student.setName(rs.getString(NAME_COLUMN));
					student.setAddress(rs.getString(ADDRESS_COLUMN));
					student.setGender(rs.getString(GENDER_COLUMN));
					student.setUserId(rs.getString(STUDENT_ID_COLUMN));
					students.add(student);
				}

				return students;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	
	public List<User> getStudentListTable(String offset) {
		
		if(offset == null) {
			offset = "0";
		}
		
		ResultSet rs = null;

		try {
			List<User> students = new ArrayList<User>();
			studentListTablePs.setString(1, USER_ROLE_ADMIN);
			studentListTablePs.setInt(2, Integer.parseInt(offset));
			rs = studentListTablePs.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					User student = new User();
					student.setName(rs.getString(NAME_COLUMN));
					student.setAddress(rs.getString(ADDRESS_COLUMN));
					student.setGender(rs.getString(GENDER_COLUMN));
					student.setUserId(rs.getString(STUDENT_ID_COLUMN));
					students.add(student);
				}

				return students;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public List<Subject> getSubjectList() {

		ResultSet rs = null;

		try {
			List<Subject> subjects = new ArrayList<Subject>();
			rs = subjectListPs.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Subject subject = new Subject();
					subject.setTitle(rs.getString(TITLE_COLUMN));
					subject.setDescription(rs.getString(DESC_COLUMN));
					subject.setSubjectId(rs.getInt(SUBJECT_ID_COLUMN));
					subjects.add(subject);
				}

				return subjects;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public int addAccount(User student) {
		ResultSet rs = null;

		try {
			addAccountPs.setString(1, student.getUsername());
			addAccountPs.setString(2, student.getPassword());
			addAccountPs.setString(3, "student");
			addAccountPs.executeUpdate();
			rs = addAccountPs.getGeneratedKeys();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {

					return rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return 0;
	}

	public boolean validateUsername(String username) {

		ResultSet rs = null;

		try {
			validateUsernamePs.setString(1, username);
			rs = validateUsernamePs.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return true;
	}

	public boolean addStudent(User student) {

		String id = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		if (validateUsername(student.getUsername())) {
			try {
				cn.setAutoCommit(false);

				int account_id = addAccount(student);

				if (account_id > 0) {

					addStudentPs.setString(1, id);
					addStudentPs.setInt(2, account_id);
					addStudentPs.setString(3, student.getName());
					addStudentPs.setString(4, student.getAddress());
					addStudentPs.setString(5, student.getGender());

					addStudentPs.executeUpdate();
					cn.commit();
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
				try {
					cn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					cn.setAutoCommit(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		return false;
	}

	public boolean validateSubject(String subject) {

		ResultSet rs = null;

		try {
			validateSubjectPs.setString(1, subject);
			rs = validateSubjectPs.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean addSubject(Subject subject) {

		if (validateSubject(subject.getTitle())) {
			try {

				addSubjectPs.setString(1, subject.getTitle());
				addSubjectPs.setString(2, subject.getDescription());
				addSubjectPs.executeUpdate();

				return true;

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return false;
	}

	public User findStudent(String studentId) {
		ResultSet rs = null;
		try {
			findStudentPs.setString(1, studentId);
			rs = findStudentPs.executeQuery();
			if (rs.next()) {
				User student = new User();
				student.setName(rs.getString(NAME_COLUMN));
				student.setAddress(rs.getString(ADDRESS_COLUMN));
				student.setGender(rs.getString(GENDER_COLUMN));
				student.setUserId(rs.getString(STUDENT_ID_COLUMN));
				student.setAccountId(rs.getString(ACCOUNT_ID_COLUMN));
				return student;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	public List<Grade> getStudentGrade(String studentId) {
		ResultSet rs = null;
		List<Grade> grades = new ArrayList<Grade>();
		try {
			gradeListPs.setString(1, studentId);
			rs = gradeListPs.executeQuery();
			while (rs.next()) {
				Grade grade = new Grade();
				grade.setTitle(rs.getString(TITLE_COLUMN));
				grade.setDescription(rs.getString(DESC_COLUMN));
				grade.setGrade(rs.getInt(GRADE_COLUMN));
				grade.setGradeId(rs.getInt(GRADE_ID_COLUMN));
				grades.add(grade);
			}

			return grades;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	public boolean validateGrade(String studentId, String subjectId) {

		ResultSet rs = null;

		try {
			validateGradePs.setString(1, studentId);
			validateGradePs.setString(2, subjectId);
			rs = validateGradePs.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return true;
	}

	public boolean addStudentGrade(String studentId, String subjectId, String grade) {
		System.out.println(VALIDATE_GRADE);
		if (validateGrade(studentId, subjectId)) {
			try {
				addGradeQueryPs.setString(1, studentId);
				addGradeQueryPs.setInt(2, Integer.parseInt(subjectId));
				addGradeQueryPs.setInt(3, Integer.parseInt(grade));
				addGradeQueryPs.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean updateStudent(User student) {

		try {
			updateStudentPs.setString(1, student.getName());
			updateStudentPs.setString(2, student.getAddress());
			updateStudentPs.setString(3, student.getGender());
			updateStudentPs.setString(4, student.getUserId());
			updateStudentPs.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteStudent(User student) {

		try {
			deleteAccountPs.setInt(1, Integer.parseInt(student.getAccountId()));
			deleteUserPs.setString(1, student.getUserId());
			deleteStudentGradePs.setString(1, student.getUserId());

			deleteAccountPs.executeUpdate();
			deleteUserPs.executeUpdate();
			deleteStudentGradePs.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateGrade(String gradeId, String grade) {

		try {

			updateGradePs.setString(1, grade);
			updateGradePs.setString(2, gradeId);
			updateGradePs.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean deleteSubject(Subject subject) {
		try {

			deleteSubjectPs.setInt(1, subject.getSubjectId());
			deleteSubjectGradePs.setInt(1, subject.getSubjectId());
			
			deleteSubjectGradePs.executeUpdate();
			deleteSubjectPs.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean checkSubjectDuplicate(Subject subject) {
		ResultSet rs = null;
		try {
			checkSubjectDuplicatePs.setString(1, subject.getTitle());
			checkSubjectDuplicatePs.setInt(2, subject.getSubjectId());
			rs = checkSubjectDuplicatePs.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return false;
	}
	
	public boolean updateSubject(Subject subject) {
		
		
		if(!checkSubjectDuplicate(subject)) {
			try {

				updateSubjectPs.setString(1, subject.getTitle());
				updateSubjectPs.setString(2, subject.getDescription());
				updateSubjectPs.setInt(3, subject.getSubjectId());
				updateSubjectPs.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
	}
	
	public List<User> searchStudent(String name){
		ResultSet rs = null;
		List<User> students = new ArrayList<User>();
		try {
			searchStudentPs.setString(1, "admin");
			searchStudentPs.setString(2, "%"+name+"%");
			rs = searchStudentPs.executeQuery();
			while(rs.next()) {
				User student = new User();
				student.setName(rs.getString(NAME_COLUMN));
				student.setAddress(rs.getString(ADDRESS_COLUMN));
				student.setGender(rs.getString(GENDER_COLUMN));
				student.setUserId(rs.getString(STUDENT_ID_COLUMN));
				students.add(student);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return students;
	}
	
	public List<Subject> searchSubject(String subjectName) {

		ResultSet rs = null;
		List<Subject> subjects = new ArrayList<Subject>();
		
		try {
			searchSubjectPs.setString(1, "%"+subjectName+"%");
			rs = searchSubjectPs.executeQuery();
			while(rs.next()) {
				Subject subject = new Subject();
				subject.setTitle(rs.getString(TITLE_COLUMN));
				subject.setDescription(rs.getString(DESC_COLUMN));
				subject.setSubjectId(rs.getInt(SUBJECT_ID_COLUMN));
				subjects.add(subject);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return subjects;
	}

}
