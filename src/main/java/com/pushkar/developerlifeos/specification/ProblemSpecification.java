package com.pushkar.developerlifeos.specification;

import com.pushkar.developerlifeos.entity.*;
import org.springframework.data.jpa.domain.Specification;

public class ProblemSpecification {

    public static Specification<Problem> hasTitle(String title){

        return (root, query, cb)->

                cb.like(

                        cb.lower(root.get("title")),

                        "%" + title.toLowerCase() + "%"

                );

    }

    public static Specification<Problem> hasDifficulty(Difficulty difficulty){

        return (root, query, cb)->

                cb.equal(root.get("difficulty"), difficulty);

    }

    public static Specification<Problem> hasPlatform(Platform platform){

        return (root, query, cb)->

                cb.equal(root.get("platform"), platform);

    }

    public static Specification<Problem> hasTopic(Topic topic){

        return (root, query, cb)->

                cb.equal(root.get("topic"), topic);

    }

    public static Specification<Problem> isSolved(boolean solved){

        return (root, query, cb)->

                cb.equal(root.get("solved"), solved);

    }

}