import { Container, Flex, Heading, Input, Textarea } from "@chakra-ui/react";
import axios from "axios";
import { useEffect, useState } from "react";
import { Button, Box } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { IUser } from "../global/Types";

const baseUrl = "http://localhost:8080";

interface IPostCreateFormProps {
  user: IUser;
}

interface IPostCreateForm {
  memberId?: number;
  title: string;
  contents: string;
}

const PostCreateForm = ({ user }: IPostCreateFormProps) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (user != null) console.table(user);
  }, []);

  const [form, setForm] = useState<IPostCreateForm>({
    memberId: user.id,
    title: "",
    contents: "",
  });

  const postSubmit = (postForm: IPostCreateForm): void => {
    axios
      .post(baseUrl + "/posts", postForm)
      .then(() => {
        alert("글을 저장했습니다.");
        navigate("/posts");
      })
      .catch((reason) => {
        const data = reason.response.data;
        console.log(reason);

        if (data.validation.title != null) {
          alert(data.validation.title);
          return;
        }

        if (data.validation.contents != null) {
          alert(data.validation.contents);
          return;
        }
      });
  };

  const changeTitle = (title: string): void => {
    setForm({ ...form, title: title });
  };

  const changeContents = (contents: string): void => {
    setForm({ ...form, contents: contents });
  };

  return (
    <>
      <Container>
        <Flex gap={100}>
          <Box>
            <Heading as={"h1"} size={"lg"}>
              글 등록
            </Heading>
            <Input
              placeholder="제목을 입력하세요"
              onChange={(e) => {
                changeTitle(e.currentTarget.value);
              }}
            />
            <Textarea
              placeholder="내용을 입력하세요"
              onChange={(e) => {
                changeContents(e.currentTarget.value);
              }}
            />

            <Button
              bg={"green.500"}
              color={"white"}
              onClick={() => {
                const confirmed = window.confirm("글을 저장하시겠습니까?");
                if (confirmed) {
                  postSubmit(form);
                }
              }}>
              전송
            </Button>
          </Box>
        </Flex>
      </Container>
    </>
  );
};

export default PostCreateForm;
