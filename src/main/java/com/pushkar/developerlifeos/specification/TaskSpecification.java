package com.pushkar.developerlifeos.specification;

import com.pushkar.developerlifeos.entity.Priority;
import com.pushkar.developerlifeos.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title){

        return (root, query, cb) ->

                cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );

    }

    public static Specification<Task>
    hasPriority(Priority priority){

        return (root, query, cb) ->

                cb.equal(
                        root.get("priority"),
                        priority
                );

    }

    public static Specification<Task>
    isCompleted(Boolean completed){

        return (root, query, cb) ->

                cb.equal(
                        root.get("completed"),
                        completed
                );

    }

}