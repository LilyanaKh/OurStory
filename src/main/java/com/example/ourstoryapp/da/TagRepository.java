package com.example.ourstoryapp.da;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.ourstoryapp.domain.Tag;
@RepositoryRestResource
public interface TagRepository extends JpaRepository<Tag, String> {
	
	
	@Query(value="SELECT tag.tag_name FROM ((story INNER JOIN memory ON story.story_id = memory.story) INNER JOIN tag_in_memory ON memory.memory_id = tag_in_memory.memory_id) INNER JOIN tag ON tag_in_memory.tag_name = tag.tag_name WHERE (((story.story_id)=?1)) GROUP BY(tag.tag_name) ORDER BY COUNT(tag.tag_name) LIMIT 3", nativeQuery = true)
	List<String> findTop3TagsByStoryId(long storyId); 
	
	
	// same as top 3 (above), top 5 & 10 
}
