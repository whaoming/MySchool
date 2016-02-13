package com.wxxiaomi.myschool.bean.office;

import java.util.List;

public class Score {
	public List<ScoreColumn> s;
	
	public static class ScoreColumn{
		/**
		 * 学年:schoolyear
		 * 学期:term
		 * 课程代码：classcode
		 * 课程名称：classname
		 * 课程性质：classnature
		 * 课程归属：belong
		 * 学分：credit
		 * 绩点：point/gpa
		 * 成绩：score
		 * 辅修标记：minor
		 * 补考成绩：makeupscore
		 * 重修成绩：rebuilescore
		 * 开课学院：begins
		 * 备注：remarks
		 * 重修标记：rebuiletab
		 */
		/**
		 * 学年
		 */
		public String schoolyear;
		/**
		 * 课程归属
		 */
		public String belong;
		/**
		 * 课程性质
		 */
		public String classnature;
		/**
		 * 学期
		 */
		public int term;
		/**
		 * 课程代码
		 */
		public int classcode;
		/**
		 * 课程名称
		 */
		public String classname;
		/**
		 * 学分
		 */
		public double credit;
		/**
		 * 绩点
		 */
		public double point;
		/**
		 * 成绩
		 */
		public double score;
		/**
		 * 辅修标记
		 */
		public int minor;
		/**
		 * 补考成绩
		 */
		public double makeupscore;
		/**
		 * 重修成绩
		 */
		public double rebuilescore;
		/**
		 * 开课学院
		 */
		public String begins;
		/**
		 * 备注
		 */
		public String remarks;
		/**
		 * 重修标记
		 */
		public int rebuiletab;
	}

}
