package com.ymw.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ymw.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, String title, String body);

	public Article getArticle(int id);

	public List<Article> getArticles(int boardId);
	
	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	public Article getForPrintArticle(int id);
	
	public int getArticlesCount(int boardId);
}