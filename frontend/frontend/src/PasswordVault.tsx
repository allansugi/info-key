import { Container, InputAdornment, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import React from "react";
import { AccountViewModel } from "./types/Account";
import { EditAccountDialog } from "./EditAccountDialog";
import NewAccountDialog from "./NewAccountDialog";
import { ResponseGetAccount } from "./types/Response";

export default function PasswordVault() {
  const [accounts, setAccounts] = React.useState<AccountViewModel[]>([]);
  const [searchResult, setSearchResult] = React.useState<AccountViewModel[]>([]);
  const [searchInput, setSearchInput] = React.useState('');
  
  // for refetching data after CRUD operation 
  const [toggle, setToggle] = React.useState(true);

  React.useEffect(() => {
    let ignore = false;

    const getAccounts = async() => {
      // credentials with JWT token
      const response = await fetch("http://localhost:8080/api/account/find/accounts", {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      });

      const responseBody: ResponseGetAccount = await response.json();
      console.log(responseBody);
      if (!ignore) {
        setAccounts(responseBody.response);
      }
    }
    
    getAccounts();

    return () => {
      ignore = true
    }
  }, [toggle])

  const handleSearch = (searchQuery: string) => {
    setSearchInput(searchQuery);
    if (searchInput === '') {
      setSearchResult(accounts);
    } else {
      setSearchResult(accounts.filter((account) => {
        return account.accountName.toLowerCase().indexOf(searchInput) !== -1 || 
              account.accountUsername.toLowerCase().indexOf(searchInput) !== -1
      }));
    }
  }

  const handleAccountChange = () => {
    setToggle(prevState => !prevState);
  } 

  return (
    <TableContainer component={Container} sx={{padding: "20px"}}>
        <Stack spacing={2}>
          <Typography variant="h4">Password Vaults</Typography>
          <Stack direction="row" spacing={2}>
              <TextField
                id="filled-search"
                label="Search field"
                type="search"
                variant="outlined"
                onChange={(e) => handleSearch(e.target.value)}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  ),
                }}
              />
            <NewAccountDialog handleNewAccount={handleAccountChange}/>
          </Stack>
        </Stack>
      <Table aria-label="password vault table">
        <TableHead>
          <TableRow>
            <TableCell><h3>ACCOUNT NAME</h3></TableCell>
            <TableCell><h3>USERNAME</h3></TableCell>
            <TableCell><h3>PASSWORD</h3></TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {
            searchInput.length > 1 ?
            searchResult.map((row, i) => (
              <EditAccountDialog
                key={i}
                listId={i}
                handleAccountChange={handleAccountChange}
                props={{
                  id: row.id, 
                  accountName: row.accountName, 
                  accountUsername: row.accountUsername, 
                  accountPassword: row.accountPassword
                }}
              />
            )) :
            accounts.map((row, i) => (
              <EditAccountDialog
                key={i}
                listId={i}
                handleAccountChange={handleAccountChange}
                props={{
                  id: row.id, 
                  accountName: row.accountName, 
                  accountUsername: row.accountUsername, 
                  accountPassword: row.accountPassword
                }}
              />
            ))
          }
        </TableBody>
      </Table>
    </TableContainer>
  )
}