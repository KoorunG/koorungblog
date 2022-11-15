import { Box, Button, Container, VStack } from "@chakra-ui/react";
import { useQuery, UseQueryResult } from "@tanstack/react-query";
import axios from "axios";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import PostPrev from "../components/PostPrev";
import { BASE_URL } from "../global/Constants";
import { userState } from "../global/State";
import { IPost } from "../global/Types";

const PostList = (): JSX.Element => {
  const user = useRecoilValue(userState);
  // const [posts, setPosts] = useState<IPost[]>([]);

  const getAllPostsRequest = async () => {
    const res = await axios.get<IPost[]>(BASE_URL + "/posts");
    return res.data;
  }

  const { data }: UseQueryResult<IPost[]> = useQuery(["getAllPosts"], getAllPostsRequest);

  // useEffect(() => {
  //   // IIFE (함수 즉시호출)
  //   (async () => {
  //     const res = await axios.get(BASE_URL + "/posts");
  //     setPosts(res.data);
  //   })();
  // }, []);

  const getUsersPosts = async () => {
    const posts = await axios.get(BASE_URL + `/members/${user.id}/posts`).catch((err) => {
      console.table(err.response.data);
    });

    if (posts) console.table(posts.data);
  };

  if (data === undefined) return <div>로딩중</div>;

  return (
    <>
      <Container maxWidth={"container.lg"}>
        <VStack alignItems="start">
          <Box mb={10}>
            {data.map((post: IPost, index: number) => {
              return <PostPrev post={post} key={post.id} />; // key값으로 Post의 식별자 사용
            })}
          </Box>
          <Box>
            <Link to="/form/posts">
              <Button size="md" bg={"green.500"} color={"white"}>
                글 등록
              </Button>
            </Link>
            <Button bg={"teal.500"} color={"white"} onClick={getUsersPosts}>
              글 가져오기
            </Button>
          </Box>
        </VStack>
      </Container>
    </>
  );
};

export default PostList;
