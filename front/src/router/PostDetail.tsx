import { Container, Heading } from "@chakra-ui/react";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import PostPrev from "../components/PostPrev";
import { BASE_URL } from "../global/Constants";
import { IPost } from "../global/Types";

const PostDetail = () => {
  const { id } = useParams();
  const [post, setPost] = useState<IPost>({ id: 0, title: "", contents: "", createdDate: "", lastModifiedDate: "" });

  useEffect(() => {
    (async () => {
      const res = await axios.get(BASE_URL + `/posts/${id}`);
      setPost(res.data);
    })();
  }, []);

  return (
    <>
      <Container>
        <Heading as="h3">상세 글 내용 ::: {id}</Heading>
        <PostPrev post={post} />
      </Container>
    </>
  );
};

export default PostDetail;
