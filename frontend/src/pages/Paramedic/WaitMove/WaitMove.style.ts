import styled from 'styled-components';

// templates
export const Container = styled.div`
  /* border: 3px solid black; */
  display: flex;
  justify-content: center;
`;

export const Wrapper = styled.div`
  /* border: 3px solid red; */
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
`;

export const ContentBox = styled.div`
  /* border: 3px solid orange; */
  margin-top: -1.5vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  border-radius: 2vh 2vh 0 0;
  z-index: 100;
  background-color: white;
  box-shadow: 0 0 1vh 0.4vh rgba(0, 0, 0, 0.1);
  padding-bottom: 5vh;
`;