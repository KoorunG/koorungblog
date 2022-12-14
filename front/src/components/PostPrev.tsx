import { Box, Heading, Text } from "@chakra-ui/react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import CustomModal from "../components/CustomModal";
import { BASE_URL } from "../global/Constants";
import { IPost } from "../global/Types";

interface IPostProps {
  post: IPost;
}

const PostPrev = ({ post }: IPostProps): JSX.Element => {
  const navigate = useNavigate();

  const deletePost = async () => {
    const isDel = window.confirm("글을 삭제하시겠습니까?");
    if (isDel) {
      await axios.delete(BASE_URL + `/posts/${post.id}`);
      window.alert("글이 삭제되었습니다.");
      navigate(0); // 새로고침
    }
  };

  return (
    <>
      <Box border={"2px"} borderRadius={10} boxShadow={"lg"} p={5} mb={5} minWidth={"container.md"}>
        <Link to={`${post.id}`}>
          <Heading as="h3" size="lg" mb={5} _hover={{ color: "blue.300" }}>
            {post.title}
          </Heading>
        </Link>
        <Text>글번호 : {post.id}</Text> {/* TODO: 식별자를 글번호로 활용 => 추후 체크*/}
        {post.author == null ? null : <Text as={"b"}>저자 : {post.author}</Text>}
        <Text noOfLines={1} mb={3}>
          내용 : {post.contents}
        </Text>
        <CustomModal
          confirmFunc={deletePost}
          buttonName="삭제"
          buttonSize="sm"
          colorScheme="red"
          modalTitle="글 삭제"
          modalBody={`${post.id}번 글을 삭제하시겠습니까?`}
        />
      </Box>
    </>
  );
};

export default PostPrev;
