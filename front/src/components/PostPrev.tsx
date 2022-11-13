import { Box, Button, Heading, Text } from "@chakra-ui/react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { BASE_URL } from "../global/Constants";
import { IPost } from "../global/Types";

interface IPostProps {
  post: IPost;
}

const PostPrev = ({ post }: IPostProps): JSX.Element => {
  const navigate = useNavigate();

  /* TODO: 글을 클릭했을 때 상세글 내역을 보여주도록 */
  const showPostDetail = async () => {
    console.log(`${post.id}번 글 클릭함`);
    navigate(`${post.id}`)
  };

  return (
    <>
      <Box mb={7} border={"1px"} borderWidth={1} borderRadius={10} boxShadow={"lg"} onClick={showPostDetail}>
        <Heading as="h3" size="lg" mb={5}>
          {post.title}
        </Heading>
        <Text>글번호 : {post.id}</Text> {/* TODO: 식별자를 글번호로 활용 => 추후 체크*/}
        {post.author == null ? null : <Text as={"b"}>저자 : {post.author}</Text>}
        <Text noOfLines={1}>내용 : {post.contents}</Text>
        {/* <Text>등록일 : {post.createdDate}</Text>
        <Text>수정일 : {post.lastModifiedDate}</Text> */}
        <Button
          onClick={() => {
            const isDel = window.confirm("글을 삭제하시겠습니까?");
            if (isDel) {
              axios.delete(BASE_URL + `/posts/${post.id}`);
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

export default PostPrev;
