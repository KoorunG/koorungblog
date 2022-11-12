import {
  Button, Modal, ModalBody,
  ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure
} from "@chakra-ui/react";

interface IControlModal {
  confirmFunc? : React.MouseEventHandler<HTMLButtonElement>;
  colorScheme? : string;
  buttonName?: string;
  modalTitle?: string;
  modalBody?: string;
}

const CustomModal = ({
  confirmFunc = () => {console.log('디폴트함수')},
  colorScheme = "teal",
  buttonName = "디폴트버튼",
  modalTitle = "디폴트타이틀",
  modalBody = "디폴트바디",
}: IControlModal) => {
  // 버튼 제어용 변수선언
  const { isOpen, onOpen, onClose } = useDisclosure();

  // 랜더링
  return (
    <>
      <Button onClick={onOpen} colorScheme={colorScheme}>{buttonName}</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{modalTitle}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>{modalBody}</ModalBody>

          <ModalFooter>
            <Button colorScheme={"red"} mr={3} onClick={onClose}>
              취소
            </Button>
            <Button colorScheme={"green"} onClick={confirmFunc}>
              확인
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default CustomModal;
