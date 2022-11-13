import { Button, Container, SimpleGrid } from "@chakra-ui/react";
import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import PostPrev from "../components/PostPrev";
import { BASE_URL } from "../global/Constants";
import { IPost, IUser } from "../global/Types";

interface IPostListPorps {
  user?: IUser;
}

const PostList = ({ user }: IPostListPorps): JSX.Element => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {

    // IIFE (함수 즉시호출)
    (async () => {
      const res = await axios.get(BASE_URL + "/posts");
      setPosts(res.data);
    })();
  }, []);

  const getAllPosts = async () => {
    const posts = await axios.get(BASE_URL + `/members/${user?.id}/posts`)
    .catch((err) => {
      console.table(err.response.data);
    });

    if(posts)
      console.table(posts.data);
  };

  return (
    <>
      <Container>
        <SimpleGrid column={3} spacing={10}>
          {posts.map((post: IPost, index: number) => {
            return <PostPrev post={post} key={post.id} />; // key값으로 Post의 식별자 사용
          })}
        </SimpleGrid>
        <Link to="/form/posts">
          <Button size="md" bg={"green.500"} color={"white"}>
            글 등록
          </Button>
        </Link>
        <Button bg={"teal.500"} color={"white"} onClick={getAllPosts}>
          글 가져오기
        </Button>
      </Container>
    </>
  );
};

export default PostList;
