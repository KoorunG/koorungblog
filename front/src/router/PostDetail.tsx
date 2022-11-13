import { Box, Button, Container, Heading, Text } from "@chakra-ui/react";
import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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

  const createComment = () => {}

  return (
    <>
      <Container maxWidth={'container.md'}>
        <Box mb={10} bg={'blackAlpha.300'}>
        <Heading as="h3">
          {post.title}
        </Heading>
        </Box>
        <Box textAlign={"end"} mb={10}>
          <Text fontSize={15}>등록일 : {post.createdDate}</Text>
          <Text fontSize={15} mb={3}>수정일 : {post.lastModifiedDate}</Text>
          <Text fontWeight={"bold"}>글쓴이 : {post.author}</Text>
        </Box>
        <Box mb={100}>
        <Text>{post.contents}</Text>
        </Box>
        <Box mb={30} bg={'blackAlpha.100'}>
          댓글영역dfdf
        </Box>
        <Box textAlign={'end'}>
            <Button onClick={createComment}>댓글달기 테스트</Button>
        </Box>
      </Container>
    </>
  );
};

export default PostDetail;
