package com.cnu.sw2023.post.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchService {
    private final PostRepository postRepository;


    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> searchPostByTitle(String keyword) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.title LIKE :keyword ORDER BY p.createdAt DESC",
                Post.class );
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    public List<Post> searchPostByContent(String keyword) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.content LIKE :keyword ORDER BY p.createdAt DESC",
                Post.class );
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    public List<Post> searchPostByTitleOrContent(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);

        Predicate titlePredicate = cb.like(root.get("title"), "%" + keyword + "%");
        Predicate contentPredicate = cb.like(root.get("content"), "%" + keyword + "%");

        Predicate orPredicate = cb.or(titlePredicate, contentPredicate);

        query.where(orPredicate);
        query.orderBy(cb.desc(root.get("createdAt")));

        TypedQuery<Post> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();

        /*
            SELECT *
            FROM Post
            WHERE title LIKE CONCAT('%', :keyword, '%')
               OR content LIKE CONCAT('%', :keyword, '%')
            ORDER BY createdAt DESC;
         */
    }

    public Page<Post> searchRestaurantPostByTitle(String keyword, String restaurantName, Pageable pageable) {
        return postRepository.findByTitleContainingAndRestaurantRestaurantName(keyword, restaurantName, pageable);
    }

    public Page<Post> searchRestaurantPostByContent(String keyword, String restaurantName, Pageable pageable) {
        return postRepository.findByContentContainingAndRestaurantRestaurantName(keyword, restaurantName, pageable);
    }
    public Page<Post> searchRestaurantPost(String keyword, String restaurantName, Pageable pageable) {
        return postRepository.findByContentContainingOrTitleContainingAndRestaurantRestaurantName(keyword, restaurantName, pageable);
    }
}
