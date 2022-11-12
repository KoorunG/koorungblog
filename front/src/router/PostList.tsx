import axios from "axios";
import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Container,
  Heading,
  SimpleGrid,
  Text,
} from "@chakra-ui/react";
import { Link, NavigateFunction, useNavigate } from "react-router-dom";
import { BASE_URL } from "../global/Constants";

interface IPost {
  id: number;
  title: string;
  contents: string;
  createdDate: string;
  lastModifiedDate: string;
}

interface IPostProps {
  post: IPost;
  index: number;
  navigate: NavigateFunction;
}

const promiseGetPosts = (
  setPosts: React.Dispatch<React.SetStateAction<IPost[]>>
) => {
  axios.get(BASE_URL + "/posts").then((res) => {
    setPosts(res.data);
  });
};

const promiseDelPost = (id: number) => {
  axios.delete(BASE_URL + `/posts/${id}`);
};

const PostList = () => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    promiseGetPosts(setPosts);
  }, []);

  const navigate = useNavigate();

  return (
    <>
      <Container>
        <SimpleGrid column={3} spacing={10}>
          {posts.map((post: IPost, index: number) => {
            return (
              <Post
                post={post}
                index={index}
                navigate={navigate}
                key={post.id}
              />
            ); // key값으로 Post의 식별자 사용
          })}
          <br />
          <br />
        </SimpleGrid>
        <Link to="/articleCreate">
          <Button size="md" bg={"green.500"} color={"white"}>
            글 등록
          </Button>
        </Link>
        <br />
        <br />
        <Button bg={"teal.500"} color={"white"} onClick={() => {}}>
          글 가져오기
        </Button>
      </Container>
    </>
  );
};

const Post: React.FC<IPostProps> = ({ post, index, navigate }): JSX.Element => {
  return (
    <>
      <Box
        mb={7}
        border={"1px"}
        borderWidth={1}
        borderRadius={10}
        boxShadow={"lg"}>
        <Heading as="h3" size="lg" mb={5}>
          {index + 1}번째 글
        </Heading>
        <Text as={"b"}>제목 : {post.title}</Text>
        <Text>내용 : {post.contents}</Text>
        <Text>등록일 : {post.createdDate}</Text>
        <Text>수정일 : {post.lastModifiedDate}</Text>
        <Button
          onClick={() => {
            const isDel = window.confirm("글을 삭제하시겠습니까?");
            if (isDel) {
              promiseDelPost(post.id);
              window.alert("글이 삭제되었습니다.");
              navigate(0); // 새로고침
            }
          }}
          bg={"red.500"}
          color={"white"}>
          삭제
        </Button>
      </Box>
    </>
  );
};

export default PostList;
