import axios from "axios";
import { useState } from "react";
import { Button, Form } from "react-bootstrap";

const baseUrl = "http://localhost:8080";

export const PostCreateForm = () => {
  interface IPostCreateForm {
    title: string;
    contents: string;
  }

  const postSubmit = (postForm: IPostCreateForm): void => {
    axios
      .post(baseUrl + "/posts", postForm)
      .then(() => {
        alert("글 전송이 완료되었습니다.");
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

  const [form, setForm] = useState<IPostCreateForm>({
    title: "",
    contents: "",
  });

  return (
    <>
      <h1>글 등록</h1>
      <br />
      <br />

      <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>제목</Form.Label>
          <Form.Control
            type="text"
            placeholder="글제목을 입력하세요"
            onChange={(e) => {
              changeTitle(e.currentTarget.value);
            }}
          />
        </Form.Group>

        <Form.Label>내용</Form.Label>
        <Form.Control
          as="textarea"
          placeholder="글내용을 입력하세요"
          style={{ height: "100px" }}
          onChange={(e) => {
            changeContents(e.currentTarget.value);
          }}
        />

        <Button
          variant="primary"
          type="button"
          onClick={() => {
            const confirmed = window.confirm("글을 저장하시겠습니까?");
            if (confirmed) {
              postSubmit(form);
            }
          }}
        >
          전송
        </Button>
      </Form>
    </>
  );
};
