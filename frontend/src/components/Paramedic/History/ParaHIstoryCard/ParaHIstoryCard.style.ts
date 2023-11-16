import styled from 'styled-components';
import theme from '/src/styles';

export const SearchList = styled.div`
  /* border: 1px solid red; */
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

export const ItemNo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  font-size: 2vh;
  padding: 1vh 0 0 0;
`

export const Item = styled.div`
  border: 0.2vh solid #D9D9D9;
  position: relative;
  margin-bottom: 1.5vh;
  padding: 6% 1.7% 3.8% 1.7%;
  width: 100%;
  background-color: ${theme.color.white};
  border-radius: 1.1vh;
`;

export const ItemRow = styled.div`
  /* border: 1px solid purple; */
  display: flex;
  justify-content: space-between;
  padding: 0 0 1vh 3vh;
`;

export const ItemCategory = styled.div`
  /* border: 1px solid blue; */
  display: flex;
  justify-content: space-between;
  text-align: center;
  color: ${theme.color.fontGrey1};
  font-size: 2vh;
  width: 16%;
  height: 2.3vh;
  `;

export const ItemContent = styled.div`
  /* border: 1px solid blue; */
  display: flex;
  justify-content: space-between;
  overflow-wrap: break-word;
  color: ${theme.color.black};
  font-size: 2vh;
  font-weight: 400;
  width: 76%;
  height: 2.6vh;
  `;

export const ItemContent2 = styled.div`
  /* border: 1px solid blue; */
  display: flex;
  justify-content: space-between;
  color: ${theme.color.black};
  font-size: 2vh;
  font-weight: 400;
  width: 76%;
  /* min-height: 4vh; */
  overflow-wrap: break-word;
`;

export const Status = styled.div`
  position: absolute;
  top: 1vh;
  right: 1vh;
  padding: 0.5vh 1vh;
  border: 0.2vh solid ${theme.color.pinkLight};
  border-radius: 1vh;
  color: ${theme.color.pinkLight};
`;
